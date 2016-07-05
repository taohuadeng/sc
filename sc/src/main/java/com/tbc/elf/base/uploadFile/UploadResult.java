package com.tbc.elf.base.uploadFile;


import java.util.Date;
import java.util.List;

/**
 * 文件上传处理结果
 */
public class UploadResult {
    /**
     * 文件处理结果参考枚举{@link Result}
     */
    private String result;

    /**
     * 处理结果详情，如果是错误为异常信息
     */
    private Object detail;

    /**
     * 错误类型，上传成功时为null,错误时参考枚举{@link ErrorType}
     */
    private String errorType;

    /**
     * 返回类型处理完后返回类型
     */
    private String responseFormat = "json";

    /**
     * 文件所属功能模块（不可为空）
     */
    private String module;

    /**
     * 文件上传时间
     */
    private Date uploadTime = new Date();

    /**
     * 文件上传花费时间
     */
    private long costTime;

    /**
     * 上传的文件列表
     */
    private List<UploadFile> files;

    /**
     * 上传文件最大大小
     */
    private long maxUploadSize;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Object getDetail() {
        return detail;
    }

    public void setDetail(Object detail) {
        this.detail = detail;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getResponseFormat() {
        return responseFormat;
    }

    public void setResponseFormat(String responseFormat) {
        this.responseFormat = responseFormat;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public long getCostTime() {
        return costTime;
    }

    public void setCostTime(long costTime) {
        this.costTime = costTime;
    }

    public List<UploadFile> getFiles() {
        return files;
    }

    public void setFiles(List<UploadFile> files) {
        this.files = files;
    }

    public long getMaxUploadSize() {
        return maxUploadSize;
    }

    public void setMaxUploadSize(long maxUploadSize) {
        this.maxUploadSize = maxUploadSize;
    }

    public enum Result {
        SUCCESS, FAILED
    }

    public enum ErrorType {
        UN_KNOW_ERROR,
        MAX_UPLOAD_SIZE,
        NO_FILE_UPLOADED,
        UPLOAD_STYLE_ERROR,
        UPLOAD_FILE_NOT_EXIST,
        MODULE_IS_EMPTY,
        PARAM_ERROR,
        FILE_NOT_EXIST,
        FILE_NOT_IMAGE,
        DRAW_IMAGE_FAILED
    }
}
