/******************************************************************************
 * Name : FollowupStatus.java Author : Administrator Date : 2010/05/08 Description : POJO implementation for FollowupStatus
 *****************************************************************************/
package com.worldvisa.business.domain.market;

import java.util.Date;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.BeanConverter;
import com.worldvisa.business.infra.base.BaseBean;

/**
 * POJO implementation for FollowupStatus.
 */
@DataTransferObject(converter = BeanConverter.class)
public class UserComment
extends BaseBean
implements Comparable<UserComment> {
    private static final long serialVersionUID = 1L;
    private String            addedBy;
    private Date              addedOn;
    private Long              commentId;
    private Communication     communication;
    private Long              communicationId;
    private String            description;
    private Long              substatus;
    private Long              userId;

    /**
     * Instantiates FollowupStatus with defaults
     */
    public UserComment() {
        super();
    }

    /**
     * Instantiates FollowupStatus with primary key combination
     */
    public UserComment(final Long commentId, final Long userId) {
        super();
        this.commentId = commentId;
        this.userId = userId;
    }

    public int compareTo(UserComment arg0) {
        return arg0.getAddedOn().compareTo(this.getAddedOn());
    }

    /**
     * @return the addedBy
     */
    public String getAddedBy() {
        return this.addedBy;
    }

    /**
     * @return the addedOn
     */
    public Date getAddedOn() {
        return this.addedOn;
    }

    /**
     * @return the commentId
     */
    public Long getCommentId() {
        return this.commentId;
    }

    /**
     * @return the communication
     */
    public Communication getCommunication() {
        return this.communication;
    }

    /**
     * @return the communicationId
     */
    public Long getCommunicationId() {
        return this.communicationId;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @return the substatus
     */
    public Long getSubstatus() {
        return this.substatus;
    }

    /**
     * @return the userId
     */
    public Long getUserId() {
        return this.userId;
    }

    /**
     * @param addedBy the addedBy to set
     */
    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    /**
     * @param addedOn the addedOn to set
     */
    public void setAddedOn(Date addedOn) {
        this.addedOn = addedOn;
    }

    /**
     * @param commentId the commentId to set
     */
    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    /**
     * @param communication the communication to set
     */
    public void setCommunication(Communication communication) {
        this.communication = communication;
    }

    /**
     * @param communicationId the communicationId to set
     */
    public void setCommunicationId(Long communicationId) {
        this.communicationId = communicationId;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @param substatus the substatus to set
     */
    public void setSubstatus(Long substatus) {
        this.substatus = substatus;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
