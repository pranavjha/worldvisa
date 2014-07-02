/******************************************************************************
 * Name : FileDetails.java Author : Administrator Date : 2010/05/08 Description : POJO implementation for FileDetails
 *****************************************************************************/
package com.worldvisa.business.domain.market;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.BeanConverter;
import com.worldvisa.business.domain.common.FileBean;
import com.worldvisa.business.infra.base.BaseBean;

/**
 * POJO implementation for FileDetails.
 */
@DataTransferObject(converter = BeanConverter.class)
public class FileDetails
extends BaseBean {
    private static final long serialVersionUID = 1L;
    private FileBean          file;
    private String            fileDesc;
    private Long              fileId;
    private Long              mailId;

    /**
     * Instantiates FileDetails with defaults
     */
    public FileDetails() {
        super();
    }

    /**
     * Instantiates FileDetails with primary key combination
     */
    public FileDetails(final Long fileId, final Long mailId) {
        super();
        this.fileId = fileId;
        this.mailId = mailId;
    }

    /**
     * @return the file
     */
    public FileBean getFile() {
        return this.file;
    }

    /**
     * @return the fileDesc
     */
    public String getFileDesc() {
        return this.fileDesc;
    }

    /**
     * @return the fileId
     */
    public Long getFileId() {
        return this.fileId;
    }

    /**
     * @return the mailId
     */
    public Long getMailId() {
        return this.mailId;
    }

    /**
     * @param file the file to set
     */
    public void setFile(final FileBean file) {
        this.file = file;
    }

    /**
     * @param fileDesc the fileDesc to set
     */
    public void setFileDesc(final String fileDesc) {
        this.fileDesc = fileDesc;
    }

    /**
     * @param fileId the fileId to set
     */
    public void setFileId(final Long fileId) {
        this.fileId = fileId;
    }

    /**
     * @param mailId the mailId to set
     */
    public void setMailId(final Long mailId) {
        this.mailId = mailId;
    }
}
