/******************************************************************************
 * Name : UserDetails.java Author : Administrator Date : 2010/05/08 Description : POJO implementation for UserDetails
 *****************************************************************************/
package com.worldvisa.business.domain.market;

import java.util.Date;
import java.util.List;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.BeanConverter;
import com.worldvisa.business.infra.base.BaseBean;

/**
 * POJO implementation for UserDetails.
 */
@DataTransferObject(converter = BeanConverter.class)
public class Communication
extends BaseBean {
    private static final long serialVersionUID = 1L;

    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return Communication.serialVersionUID;
    }

    private Long              actionType;
    private String            addedBy;
    private List<FileDetails> attachments;
    private String            mailBody;
    private String            mailSubject;
    private Date              sentTime;
    private Long              seqNum;

    /**
     * 
     */
    public Communication() {
    }

    public Communication(final Long seqNum) {
        this.seqNum = seqNum;
    }

    /**
     * @return the actionType
     */
    public Long getActionType() {
        return this.actionType;
    }

    /**
     * @return the addedBy
     */
    public String getAddedBy() {
        return this.addedBy;
    }

    /**
     * @return the attachments
     */
    public List<FileDetails> getAttachments() {
        return this.attachments;
    }

    /**
     * @return the mailBody
     */
    public String getMailBody() {
        return this.mailBody;
    }

    /**
     * @return the mailSubject
     */
    public String getMailSubject() {
        return this.mailSubject;
    }

    /**
     * @return the sentTime
     */
    public Date getSentTime() {
        return this.sentTime;
    }

    /**
     * @return the seqNum
     */
    public Long getSeqNum() {
        return this.seqNum;
    }

    /**
     * @param actionType the actionType to set
     */
    public void setActionType(final Long actionType) {
        this.actionType = actionType;
    }

    /**
     * @param addedBy the addedBy to set
     */
    public void setAddedBy(final String addedBy) {
        this.addedBy = addedBy;
    }

    /**
     * @param attachments the attachments to set
     */
    public void setAttachments(final List<FileDetails> attachments) {
        this.attachments = attachments;
    }

    /**
     * @param mailBody the mailBody to set
     */
    public void setMailBody(final String mailBody) {
        this.mailBody = mailBody;
    }

    /**
     * @param mailSubject the mailSubject to set
     */
    public void setMailSubject(final String mailSubject) {
        this.mailSubject = mailSubject;
    }

    /**
     * @param sentTime the sentTime to set
     */
    public void setSentTime(final Date sentTime) {
        this.sentTime = sentTime;
    }

    /**
     * @param seqNum the seqNum to set
     */
    public void setSeqNum(final Long seqNum) {
        this.seqNum = seqNum;
    }
}