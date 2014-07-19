package com.worldvisa.business.infra.exception;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.Param;
import org.directwebremoting.convert.ExceptionConverter;
import com.worldvisa.business.infra.base.BaseException;

@DataTransferObject(converter = ExceptionConverter.class, params = {
    @Param(name = "include", value = "type")
})
public class AuthorizationException
extends BaseException {
    private static final long serialVersionUID = 6730697730278672038L;
}
