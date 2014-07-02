/******************************************************************************
 * Name : TracingAspect.java Author : pranav_jha Date : 2010/02/20 Description : Aspect to handle tracing of methods at multiple layers
 *****************************************************************************/
package com.worldvisa.business.infra.aspects;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Aspect to handle tracing of methods at multiple layers
 */
@Component
@Aspect
public class TracingAspect {
    private static final Logger log = Logger.getLogger(TracingAspect.class);

    @Around("com.worldvisa.business.infra.aspects.SystemPointcuts.inWebLayer() || com.worldvisa.business.infra.aspects.SystemPointcuts.inServiceLayer() || com.worldvisa.business.infra.aspects.SystemPointcuts.inDataAccessLayer()")
    public Object invoke(final ProceedingJoinPoint joinPoint)
    throws Throwable {
        final StopWatch sw = new StopWatch();
        final String method = joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName();
        TracingAspect.log.debug(">>>>>               : " + method);
        sw.start();
        Object obj = null;
        try {
            obj = joinPoint.proceed();
            return obj;
        } finally {
            sw.stop();
            TracingAspect.log.debug("<<<<<     " + String.format("%1$6d ms : ", sw.getTime()) + method);
        }
    }
}
