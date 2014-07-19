/******************************************************************************
 * Name : FileDetails.java Author : Administrator Date : 2010/05/08 Description : POJO implementation for FileDetails
 *****************************************************************************/
package com.worldvisa.business.domain.workflow;

import java.util.Date;
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
    private String            reportId;
    private String            uploadedBy;
    private Date              uploadedDt;

    /**
     * Instantiates FileDetails with defaults
     */
    public FileDetails() {
        super();
    }

    /**
     * Instantiates FileDetails with primary key combination
     */
    public FileDetails(final Long fileId, final String reportId) {
        super();
        this.fileId = fileId;
        this.reportId = reportId;
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
     * @return the reportId
     */
    public String getReportId() {
        return this.reportId;
    }

    /**
     * @return the uploadedBy
     */
    public String getUploadedBy() {
        return this.uploadedBy;
    }

    /**
     * @return the uploadedDt
     */
    public Date getUploadedDt() {
        return this.uploadedDt;
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
     * @param reportId the reportId to set
     */
    public void setReportId(final String reportId) {
        this.reportId = reportId;
    }

    /**
     * @param uploadedBy the uploadedBy to set
     */
    public void setUploadedBy(final String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    /**
     * @param uploadedDt the uploadedDt to set
     */
    public void setUploadedDt(final Date uploadedDt) {
        this.uploadedDt = uploadedDt;
    }
}
