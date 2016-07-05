package com.tbc.elf.base.uploadFile;

import com.google.gson.Gson;
import com.tbc.elf.base.security.util.AuthenticationUtil;
import com.tbc.elf.base.util.UUIDGenerator;
import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * 文件上传下载controller
 */
@RequestMapping("/file")
@Controller
public class UploadFileController {

    /**
     * 日志
     */
    private static final Log LOG = LogFactory.getLog(UploadFileController.class);

    /**
     * 下载文件名称编码工具
     */
    private static final URLCodec urlCodec = new URLCodec();

    /**
     * json转化工具
     */
    private static final Gson GSON = new Gson();

    /**
     * 上传文件保存名称
     */
    private static final String FILE_UPLOAD_DATA = "data";

    /**
     * 上传文件元数据保存文件名
     */
    private static final String FILE_UPLOAD_METADATA = "metadata";

    /**
     * 文件分割符
     */
    private static final String FILE_SEPARATOR = File.separator;

    /**
     * 文件上传解析器
     */
    @Resource
    private MultipartResolver multipartResolver;

    /**
     * 上传文档保存的公共路径
     */
    /*@Value("${uploadFile.commonFilePath}")*/
    private String commonFilePath = "/web/file/";


    @RequestMapping(value = "/index")
    public String index() {
        return "index";
    }

    @ResponseBody
    @RequestMapping(value = "/handleMaxUploadSizeException")
    public UploadResult handleMaxUploadSizeException(HttpServletRequest request) {
        UploadResult uploadResult = new UploadResult();
        uploadResult.setMaxUploadSize(((MaxUploadSizeExceededException)
                request.getAttribute(SimpleMappingExceptionResolver.DEFAULT_EXCEPTION_ATTRIBUTE))
                .getMaxUploadSize());
        uploadResult.setResult(UploadResult.Result.FAILED.name());
        uploadResult.setErrorType(UploadResult.ErrorType.MAX_UPLOAD_SIZE.name());

        return uploadResult;
    }

    @ResponseBody
    @RequestMapping(value = "/cutImage")
    public CutImageResult cutImage(HttpServletRequest request) {
        CutImageResult cir = new CutImageResult();
        new ServletRequestDataBinder(cir).bind(request);
        String fileId = cir.getFileId();
        String module = cir.getModule();
        Integer x = cir.getX();
        Integer y = cir.getY();
        Integer width = cir.getWidth();
        Integer height = cir.getHeight();
        if (StringUtils.isEmpty(fileId) || StringUtils.isEmpty(module)
                || x == null || y == null || width == null || height == null) {
            cir.setResult(UploadResult.Result.FAILED.name());
            cir.setErrorType(UploadResult.ErrorType.PARAM_ERROR.name());
            return cir;
        }

        String parentPath = getParentPath(module, fileId);
        try {
            String fileName = "x" + x + "y" + y + "w" + width + "h" + height;
            String crop = "crop=" + width + ":" + height + ":" + x + ":" + y + " ";
            String command = "ffmpeg -i " + parentPath + FILE_UPLOAD_DATA
                    + " -y -f image2 -vf " + crop + parentPath + fileName;
            Runtime.getRuntime().exec(command);
            cir.setFileName(fileName);
            cir.setResult(UploadResult.Result.SUCCESS.name());
        } catch (Throwable e) {
            cir.setResult(UploadResult.Result.FAILED.name());
            cir.setErrorType(UploadResult.ErrorType.DRAW_IMAGE_FAILED.name());
            cir.setDetail(e);
        }

        return cir;
    }

    private String getParentPath(String module, String fileId) {
        return commonFilePath + module + FILE_SEPARATOR + AuthenticationUtil.getCorpCode()
                + FILE_SEPARATOR + fileId + FILE_SEPARATOR;
    }

    @ResponseBody
    @RequestMapping(value = "/uploadFile")
    public UploadResult uploadFile(HttpServletRequest request) {
        UploadResult uploadResult = new UploadResult();
        if (!multipartResolver.isMultipart(request)) {
            uploadResult.setResult(UploadResult.Result.FAILED.name());
            uploadResult.setErrorType(UploadResult.ErrorType.UPLOAD_STYLE_ERROR.name());
            return uploadResult;
        }

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Iterator<String> fileNames = multipartRequest.getFileNames();
        if (fileNames == null || !fileNames.hasNext()) {
            uploadResult.setResult(UploadResult.Result.FAILED.name());
            uploadResult.setErrorType(UploadResult.ErrorType.NO_FILE_UPLOADED.name());
            return uploadResult;
        }

        new ServletRequestDataBinder(uploadResult).bind(request);
        String module = uploadResult.getModule();
        if (StringUtils.isEmpty(module)) {
            uploadResult.setResult(UploadResult.Result.FAILED.name());
            uploadResult.setErrorType(UploadResult.ErrorType.MODULE_IS_EMPTY.name());
            return uploadResult;
        }

        List<UploadFile> uploadFiles = new ArrayList<>();
        uploadResult.setFiles(uploadFiles);
        List<String> fileIds = new ArrayList<>();
        try {
            while (fileNames.hasNext()) {
                String fileId = UUIDGenerator.uuid();
                fileIds.add(fileId);
                MultipartFile file = multipartRequest.getFile(fileNames.next());
                if (file == null) {
                    deleteFile(fileIds, module);
                    uploadResult.setResult(UploadResult.Result.FAILED.name());
                    uploadResult.setErrorType(UploadResult.ErrorType.UPLOAD_FILE_NOT_EXIST.name());
                    return uploadResult;
                }

                uploadFiles.add(uploadFile(fileId, module, file));
            }

            uploadResult.setCostTime(new Date().getTime() - uploadResult.getUploadTime().getTime());
            uploadResult.setResult(UploadResult.Result.SUCCESS.name());
        } catch (Exception e) {
            uploadResult.setResult(UploadResult.Result.FAILED.name());
            uploadResult.setErrorType(UploadResult.ErrorType.UN_KNOW_ERROR.name());
            uploadResult.setDetail(e);
            deleteFile(fileIds, module);
        }

        return uploadResult;
    }

    private UploadFile uploadFile(String fileId, String module, MultipartFile file) throws Exception {
        UploadFile uploadFile = new UploadFile();
        uploadFile.setFileId(fileId);
        String filename = file.getOriginalFilename();
        uploadFile.setFileName(filename);
        uploadFile.setModule(module);
        uploadFile.setSuffix(filename.contains(".") ? filename.substring(filename.indexOf(".") + 1) : null);
        uploadFile.setFileSize(file.getSize());
        uploadFile.setContentType(file.getContentType());
        uploadFile.setOwner(AuthenticationUtil.getUserId());

        String parentPath = getParentPath(module, fileId);
        File parent = new File(parentPath);
        if (!parent.mkdirs()) {
            throw new IOException("Create directory[" + parentPath + "] failed!");
        }

        file.transferTo(new File(parent, FILE_UPLOAD_DATA));
        uploadFile.setCostTime(System.currentTimeMillis() - uploadFile.getUploadTime().getTime());
        OutputStream output = null;
        try {
            output = new FileOutputStream(new File(parent, FILE_UPLOAD_METADATA));
            IOUtils.write(GSON.toJson(uploadFile), output);
        } finally {
            IOUtils.closeQuietly(output);
        }

        return uploadFile;
    }

    private void deleteFile(List<String> fileIds, String module) {
        if (CollectionUtils.isEmpty(fileIds)) {
            return;
        }

        String path = commonFilePath + module + FILE_SEPARATOR + AuthenticationUtil.getCorpCode()
                + FILE_SEPARATOR;
        for (String fileId : fileIds) {
            File file = new File(path + fileId + FILE_SEPARATOR);
            if (!file.exists()) {
                continue;
            }

            try {
                FileUtils.forceDelete(file);
            } catch (Exception e) {
                LOG.debug("Delete file[" + path + fileId + FILE_SEPARATOR + "]", e);
            }
        }
    }

    @RequestMapping(value = "/getFile/{module}/{fileId}")
    public void getFile(@PathVariable("module") String module, @PathVariable("fileId") String fileId
            , HttpServletRequest request, HttpServletResponse response) {
        getFile(request, response, getParentPath(module, fileId), null, false);
    }

    @RequestMapping(value = "/getFile/{module}/{fileId}/{fileName}")
    public void getFile(@PathVariable("module") String module, @PathVariable("fileId") String fileId
            , @PathVariable("fileName") String fileName, HttpServletRequest request
            , HttpServletResponse response) throws Exception {
        getFile(request, response, getParentPath(module, fileId), fileName, false);
    }

    @RequestMapping(value = "/downloadFile/{module}/{fileId}")
    public void downloadFile(@PathVariable("module") String module, @PathVariable("fileId") String fileId
            , HttpServletRequest request, HttpServletResponse response) {
        getFile(request, response, getParentPath(module, fileId), null, true);
    }

    @RequestMapping(value = "/downloadFile/{module}/{fileId}/{fileName}")
    public void downloadFile(@PathVariable("module") String module, @PathVariable("fileId") String fileId
            , @PathVariable("fileName") String fileName, HttpServletRequest request
            , HttpServletResponse response) throws Exception {
        getFile(request, response, getParentPath(module, fileId), fileName, true);
    }

    private void getFile(HttpServletRequest request, HttpServletResponse response
            , String parentPath, String fileName, boolean download) {
        InputStream mdInputStream = null;
        InputStream dataInputStream = null;
        try {
            mdInputStream = new BufferedInputStream(new FileInputStream(new File(parentPath + FILE_UPLOAD_METADATA)));
            String metadata = IOUtils.toString(mdInputStream);
            UploadFile uploadFile = GSON.fromJson(metadata, UploadFile.class);
            response.setCharacterEncoding("utf-8");
            response.setContentType(uploadFile.getContentType());
            String attachmentFileName = StringUtils.isEmpty(fileName) ? uploadFile.getFileName()
                    : fileName + (StringUtils.isEmpty(uploadFile.getSuffix()) ? "" : "." + uploadFile.getSuffix());
            response.setHeader("Content-Disposition", (download ? "attachment;" : "") + "fileName="
                    + toAttachmentFileName(attachmentFileName, request));
            dataInputStream = new BufferedInputStream(new FileInputStream(parentPath
                    + (StringUtils.isEmpty(fileName) ? FILE_UPLOAD_DATA : fileName)));
            IOUtils.copy(dataInputStream, response.getOutputStream());
        } catch (Throwable e) {
            throw new GetFileException("File[url:" + request.getRequestURI()
                    + ",filePath:" + parentPath + (StringUtils.isEmpty(fileName) ? FILE_UPLOAD_DATA : fileName)
                    + "] not exist!", e);
        } finally {
            IOUtils.closeQuietly(mdInputStream);
            IOUtils.closeQuietly(dataInputStream);
        }
    }

    private String toAttachmentFileName(String fileName, HttpServletRequest request) {
        try {
            String userAgent = request.getHeader("User-Agent");
            if (StringUtils.isEmpty(userAgent)) {
                return urlCodec.encode(fileName);
            }

            if (userAgent.contains("Firefox") || userAgent.contains("Chrome")) {
                return new String(fileName.getBytes("UTF-8"), "ISO8859-1")
                        .replace(' ', '_').replace('=', '_').replace(',', '_').replace('&', '_');

            }

            return urlCodec.encode(fileName).replaceAll("[+]", " ");
        } catch (Throwable e) {
            return fileName;
        }
    }

}
