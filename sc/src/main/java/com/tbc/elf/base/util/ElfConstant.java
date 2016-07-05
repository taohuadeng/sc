package com.tbc.elf.base.util;

/**
 * ELF常量
 *
 * @author ELF@TEAM
 * @since 2016年2月24日 17:33:50
 */
public interface ElfConstant {

    /**
     * 层级数据根节点的 parentId
     */
    String ROOT_PARENT_ID = "*";

    /**
     * 正斜杠 /
     */
    String FORWARD_SLASH = "/";

    /**
     * 反斜杠 \
     */
    String BACK_SLASH = "\\";

    /**
     * 点
     */
    String DOT = ".";

    /**
     * 公司编号
     */
    String CORP_CODE = "corpCode";

    /**
     * 登录用户名
     */
    String J_USERNAME = "j_username";

    //申请加入
    String GROUP_JOIN_REQ_TYPE_REQUEST = "REQUEST";
    String GROUP_STATUS_ACTIVE = "ACTIVE"; //启用
    String VID_OPEN = "OPEN"; //开放
    String VID_DYNAMIC = "DYNAMIC"; //条件
    String VID_APPOINT = "APPOINT"; //不开放
    String VID_VALIDATE = "VALIDATE"; //验证
    //已通过申请加入
    String GROUP_JOIN_REQ_TYPE_ACCEPT = "ACCEPT";

}
