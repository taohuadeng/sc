package com.tbc.elf.base.security.model;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

/**
 * 提供资源信息
 */
public interface ResourceDetails extends Serializable {

    /**
     * 资源串
     */
    public String getResString();

    /**
     * 资源类型,如URL,FUNCTION
     */
    public String getResType();

    /**
     * 返回属于该resource的authorities
     */
    public GrantedAuthority getAuthoritie();

    public void setAuthoritie(GrantedAuthority authoritie);
}
