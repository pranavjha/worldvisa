/******************************************************************************
 * Name : Notification.java Author : Administrator Date : 2010/05/08 Description : POJO implementation for Notification
 *****************************************************************************/
package com.worldvisa.business.domain.workflow;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.BeanConverter;
import com.worldvisa.business.infra.base.BaseBean;
import com.worldvisa.business.infra.constants.LookupConstants;
import com.worldvisa.business.infra.utils.BasicUtils;

/**
 * POJO implementation for Notification.
 */
@DataTransferObject(converter = BeanConverter.class)
public class Notification
extends BaseBean {
    private static final long serialVersionUID = 1L;
    private String            byUserId;
    private java.util.Date    creationTime;
    private String            forUserId;
    private String            notificationComment;
    private Long              notificationId;
    private Long              notificationStatus;
    private java.util.Date    notificationTime;
    private String            reportId;

    /**
     * Instantiates Notification with defaults
     */
    public Notification() {
        super();
    }

    /**
     * Instantiates Notification with primary key combination
     */
    public Notification(final Long notificationId, final Long notificationStatus, final String reportId) {
        super();
        this.notificationId = notificationId;
        this.notificationStatus = notificationStatus;
        this.reportId = reportId;
    }

    /**
     * Getter method for byUserId
     * @return byUserId
     */
    public String getByUserId() {
        return this.byUserId;
    }

    /**
     * Getter method for creationTime
     * @return creationTime
     */
    public java.util.Date getCreationTime() {
        return this.creationTime;
    }

    /**
     * Getter method for forUserId
     * @return forUserId
     */
    public String getForUserId() {
        return this.forUserId;
    }

    /**
     * Getter method for notificationComment
     * @return notificationComment
     */
    public String getNotificationComment() {
        return this.notificationComment;
    }

    public String getNotificationDelta() {
        try {
            if (LookupConstants.NOTIFICATION_STATUS.Completed.equals(this.getNotificationStatus())
            || LookupConstants.NOTIFICATION_STATUS.Abandoned.equals(this.getNotificationStatus())) {
                return "not applicable";
            }
            String retString = null;
            long minDiff = (this.notificationTime.getTime() - BasicUtils.getCurrentDateTime().getTime()) / (60 * 1000);
            if (minDiff < 0) {
                retString = " overdue";
                minDiff = -minDiff;
            } else {
                retString = " pending";
            }
            return (minDiff / 60) + " Hour(s) " + (minDiff % 60) + " Min(s) " + retString;
        } catch (final Exception e) {
            return "";
        }
    }

    /**
     * Getter method for notificationId
     * @return notificationId
     */
    public Long getNotificationId() {
        return this.notificationId;
    }

    /**
     * Getter method for notificationStatus
     * @return notificationStatus
     */
    public Long getNotificationStatus() {
        return this.notificationStatus;
    }

    /**
     * Getter method for byUserId
     * @return byUserId
     */
    public String getNotificationStatusColor() {
        try {
            if (LookupConstants.NOTIFICATION_STATUS.Completed.equals(this.getNotificationStatus())
            || LookupConstants.NOTIFICATION_STATUS.Abandoned.equals(this.getNotificationStatus())) {
                return "background-color: #C0C0C0;";
            }
            // l will range from -255 to 255
            final long l = (this.getNotificationTime().getTime() - BasicUtils.getCurrentDateTime().getTime()) / 168750L + 255L;
            int red = 0;
            int green = 0;
            if (l < 255L) {
                red = 255;
                green = (int) l;
            } else {
                red = 511 - (int) l;
                green = 255;
            }
            red = red < 0 ? 0 : red;
            green = green < 0 ? 0 : green;
            red = red > 255 ? 255 : red;
            green = green > 255 ? 255 : green;
            return "background-color: rgb(" + red + "," + green + ",0);";
        } catch (final Exception e) {
            return "background-color: #FFFFFF;";
        }
    }

    /**
     * Getter method for notificationTime
     * @return notificationTime
     */
    public java.util.Date getNotificationTime() {
        return this.notificationTime;
    }

    /**
     * Getter method for reportId
     * @return reportId
     */
    public String getReportId() {
        return this.reportId;
    }

    /**
     * Setter method for byUserId
     * @param byUserId Value for byUserId
     */
    public void setByUserId(final String byUserId) {
        this.byUserId = byUserId;
    }

    /**
     * Setter method for creationTime
     * @param creationTime Value for creationTime
     */
    public void setCreationTime(final java.util.Date creationTime) {
        this.creationTime = creationTime;
    }

    /**
     * Setter method for forUserId
     * @param forUserId Value for forUserId
     */
    public void setForUserId(final String forUserId) {
        this.forUserId = forUserId;
    }

    /**
     * Setter method for notificationComment
     * @param notificationComment Value for notificationComment
     */
    public void setNotificationComment(final String notificationComment) {
        this.notificationComment = notificationComment;
    }

    /**
     * Setter method for notificationId
     * @param notificationId Value for notificationId
     */
    public void setNotificationId(final Long notificationId) {
        this.notificationId = notificationId;
    }

    /**
     * Setter method for notificationStatus
     * @param notificationStatus Value for notificationStatus
     */
    public void setNotificationStatus(final Long notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    /**
     * Setter method for notificationTime
     * @param notificationTime Value for notificationTime
     */
    public void setNotificationTime(final java.util.Date notificationTime) {
        this.notificationTime = notificationTime;
    }

    /**
     * Setter method for reportId
     * @param reportId Value for reportId
     */
    public void setReportId(final String reportId) {
        this.reportId = reportId;
    }
}
