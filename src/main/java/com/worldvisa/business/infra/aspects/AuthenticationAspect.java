package com.worldvisa.business.infra.aspects;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import com.worldvisa.business.infra.base.BaseView;
import com.worldvisa.business.infra.exception.AutenticationException;

@Component
@Aspect
public class AuthenticationAspect {
    private static Logger log = Logger.getLogger(AuthenticationAspect.class);

    @Around("com.worldvisa.business.infra.aspects.SystemPointcuts.inWebLayer() "
    + "&& !execution(@com.worldvisa.business.infra.annotations.AuthenticationBypass public * *(..))")
    public Object handleWebLayerException(final ProceedingJoinPoint joinPoint)
    throws Throwable {
        final BaseView view = (BaseView) joinPoint.getThis();
        if (view.user() == null) {
            AuthenticationAspect.log.debug("authentication failed for method: " + joinPoint.getSignature());
            throw new AutenticationException();
        }
        return joinPoint.proceed();
    }
}
