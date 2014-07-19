package com.worldvisa.business.domain.market;

import java.util.Date;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.BeanConverter;
import com.worldvisa.business.infra.base.BaseBean;

/**
 * 
 */
@DataTransferObject(converter = BeanConverter.class)
public class UserGroupComment
extends BaseBean {
    private static final long serialVersionUID = -3859194860909604189L;
    private String            addedBy;
    private Date              addedOn;
    private Long              commentId;
    private Communication     communication;
    private Long              communicationId;
    private String            description;
    private Long              substatus;
    private Long              userGroupId;

    public UserGroupComment() {
    }

    public UserGroupComment(Long commentId, Long userGroupId) {
        this.commentId = commentId;
        this.userGroupId = userGroupId;
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
     * @return the userGroupId
     */
    public Long getUserGroupId() {
        return this.userGroupId;
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
     * @param userGroupId the userGroupId to set
     */
    public void setUserGroupId(Long userGroupId) {
        this.userGroupId = userGroupId;
    }

    public UserComment translateToUserComment(Long userId) {
        final UserComment comment = new UserComment();
        comment.setAddedBy(this.addedBy);
        comment.setAddedOn(this.addedOn);
        comment.setCommentId(this.commentId);
        comment.setCommunicationId(this.communicationId);
        comment.setDescription("(Group) " + this.description);
        comment.setSubstatus(this.substatus);
        comment.setUserId(userId);
        return comment;
    }
}
