package com.tbc.elf.app.uc.service;

import com.tbc.elf.app.uc.model.Setting;
import com.tbc.elf.base.security.util.AuthenticationUtil;
import com.tbc.elf.base.service.BaseServiceImpl;
import com.tbc.elf.base.service.HibernateBaseService;
import com.tbc.elf.base.util.HqlBuilder;
import com.tbc.elf.base.util.UUIDGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("settingService")
public class SettingServiceImpl extends BaseServiceImpl<Setting> implements SettingService  {
    @Resource
    protected HibernateBaseService baseService;

    @Override
    @Transactional(readOnly = false,isolation = Isolation.READ_COMMITTED)
    public Setting getCorpSetting() {
        String corpCode = AuthenticationUtil.getCorpCode();
        if(!checkExistCorpCode(corpCode)){
            addSetting(corpCode);
        }

        return loadSettingByCorpCode(corpCode);
    }

    @Override
    @Transactional(readOnly = true,isolation = Isolation.READ_COMMITTED)
    public Setting loadSettingByCorpCode(String corpCode) {
        HqlBuilder builder = new HqlBuilder("FROM Setting WHERE corpCode = ?");
        builder.addParameter(corpCode);
        return baseService.queryUniqueResult(builder);
    }

    /*
     * 查询当前公司是否存在设置
     * @param corpCode 公司代码
     * @return true 存在 ，false 不存在
     */
    private Boolean checkExistCorpCode(String corpCode) {
        HqlBuilder builder = new HqlBuilder();
        builder.append("SELECT count(*) FROM  Setting WHERE corpCode = ?");
        builder.addParameter(corpCode);
        Long count = baseService.queryUniqueResult(builder);
        return count > 0;
    }

    /*
     * 根据当前公司代码添加设置
     * @param corpCode 公司代码
     */
    private void addSetting(String corpCode) {
        Setting setting = new Setting();
        setting.setSettingId(UUIDGenerator.uuid()); //主键
        //冻结
        setting.setUserFreezeEmailFlag(true);
        setting.setUserFreezeInternalSmsFlag(true);
        setting.setUserFreezeSmsFlag(false);
        //找回密码发送验证码
        setting.setSendVerifyCodeEmailFlag(true);
        setting.setSendVerifyCodeInternalSmsFlag(true);
        setting.setSendVerifyCodeSmsFlag(false);
        //新增用户
        setting.setAddUserEmailFlag(true);
        setting.setAddUserInternalSmsFlag(true);
        setting.setAddUserSmsFlag(false);
        //学员账号激活
        setting.setUserActivateEmailFlag(true);
        setting.setUserActivateInternalSmsFlag(true);
        setting.setUserActivateSmsFlag(false);
        //重置密码
        setting.setResetPwdEmailFlag(true);
        setting.setResetPwdInternalSmsFlag(true);
        setting.setResetPwdSmsFlag(false);
        //部门调动
        setting.setDeptChangeEmailFlag(true);
        setting.setDeptChangeInternalSmsFlag(true);
        setting.setDeptChangeSmsFlag(false);
        //找回密码申请
        setting.setFindPwdApplyEmailFlag(true);
        setting.setFindPwdApplyInternalSmsFlag(true);
        setting.setFindPwdApplySmsFlag(false);
        //找回密码
        setting.setRetrievePwdEmailFlag(true);
        setting.setRetrievePwdInternalSmsFlag(true);
        setting.setRetrievePwdSmsFlag(false);
        setting.setCorpCode(corpCode);

        baseService.save(setting);
    }
}
