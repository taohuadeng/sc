package com.tbc.elf.base.util;

/**
 * SQL 实用类
 *
 * @author ELF@TEAM
 * @since 2016年2月29日 11:23:56
 *
 */
public class SqlUtil {

    /**
     * escape like条件的保留字符(_,%，\）
     *
     * @param value like条件的内容
     * @return escape后的结果
     */
    public static String escapeLike(String value) {
        // Must put the \\ replacement as the first.
        value = value.replace("\\", "\\\\");
        value = value.replace("%", "\\%");
        value = value.replace("_", "\\_");
        return value;
    }
}
