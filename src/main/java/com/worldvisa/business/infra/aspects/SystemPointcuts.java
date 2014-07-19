/******************************************************************************
 * Name : SystemPointcuts.java Author : samiry_vaidya Date : 2010/02/22 Description : Aspect to define all common pointcuts to be used across all
 * aspects
 *****************************************************************************/
package com.worldvisa.business.infra.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Aspect to define all common pointcuts to be used across all aspects
 */
@Aspect
public class SystemPointcuts {
    @Pointcut("within(@org.springframework.stereotype.Repository com.worldvisa..*) && execution(public * *(..))")
    public void inDataAccessLayer() {
    }

    @Pointcut("within(@org.springframework.stereotype.Service com.worldvisa..*) && execution(public * *(..))")
    public void inServiceLayer() {
    }

    @Pointcut("within(@org.directwebremoting.annotations.RemoteProxy com.worldvisa..*)"
    + " && execution(@org.directwebremoting.annotations.RemoteMethod public * *(..))")
    public void inWebLayer() {
    }
}
