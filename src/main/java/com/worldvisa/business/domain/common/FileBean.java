package com.worldvisa.business.domain.common;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.BeanConverter;
import com.worldvisa.business.infra.base.BaseBean;

/**
 * Defines FileRequestObject
 */
@DataTransferObject(converter = BeanConverter.class)
public class FileBean
extends BaseBean {
    private static final long serialVersionUID = 142992609626736911L;
    private String            fileName;
    private String            relativePath;

    /**
     * @return the fileName
     */
    public String getFileName() {
        return this.fileName;
    }

    /**
     * @return the relativePath
     */
    public String getRelativePath() {
        return this.relativePath;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }

    /**
     * @param relativePath the relativePath to set
     */
    public void setRelativePath(final String relativePath) {
        this.relativePath = relativePath;
    }
}
