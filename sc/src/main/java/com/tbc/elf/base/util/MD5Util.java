package com.tbc.elf.base.util;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

/**
 * 这个类用于帮助开发人员快速生成MD5编码的字符串
 *
 * @author ELF@TEAM
 * @since 2016年2月25日 10:41:32
 */
public class MD5Util {

    public static String md5(String value) {
        Md5PasswordEncoder encoder = new Md5PasswordEncoder();
        //encoder.setEncodeHashAsBase64(true);
        return encoder.encodePassword(value, "");
    }
}
