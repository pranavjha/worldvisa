/******************************************************************************
 * Name : ExceptionHandlingAspect.java Author : samiry_vaidya Date : 2010/02/23 Description : Aspect to handle exceptions at multiple layers
 *****************************************************************************/
package com.worldvisa.business.infra.aspects;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import com.worldvisa.business.infra.constants.MessageSeverity;
import com.worldvisa.business.infra.exception.DWRException;

/**
 * Aspect to handle exceptions at multiple layers
 */
@Component
@Aspect
public class ExceptionHandlingAspect {
    private static final Logger log = Logger.getLogger(ExceptionHandlingAspect.class);

    @AfterThrowing(pointcut = "com.worldvisa.business.infra.aspects.SystemPointcuts.inServiceLayer()"
    + " || com.worldvisa.business.infra.aspects.SystemPointcuts.inDataAccessLayer()", throwing = "ex")
    public void handleBizLayerException(final Throwable ex)
    throws Throwable {
        throw ex;
    }

    @Around("com.worldvisa.business.infra.aspects.SystemPointcuts.inWebLayer()")
    public Object handleWebLayerException(final ProceedingJoinPoint joinPoint) {
        try {
            return joinPoint.proceed();
        } catch (final DWRException th) {
            throw th;
        } catch (final Throwable th) {
            ExceptionHandlingAspect.log.error("exception:", th);
            throw new DWRException(th, MessageSeverity.ERROR);
        }
    }
}
