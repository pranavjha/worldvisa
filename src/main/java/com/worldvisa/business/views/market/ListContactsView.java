/**
 * 
 */
package com.worldvisa.business.views.market;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.io.FileTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import com.worldvisa.business.domain.common.DWRResponse;
import com.worldvisa.business.domain.common.FileBean;
import com.worldvisa.business.domain.common.WebMessage;
import com.worldvisa.business.domain.market.Communication;
import com.worldvisa.business.domain.market.FileDetails;
import com.worldvisa.business.domain.market.User;
import com.worldvisa.business.domain.market.UserComment;
import com.worldvisa.business.domain.market.UserFilter;
import com.worldvisa.business.domain.market.UserGroup;
import com.worldvisa.business.domain.market.UserGroupFilter;
import com.worldvisa.business.domain.workflow.Notification;
import com.worldvisa.business.infra.base.BaseView;
import com.worldvisa.business.infra.constants.LookupConstants;
import com.worldvisa.business.infra.constants.MessageSeverity;
import com.worldvisa.business.infra.constants.ReportType;
import com.worldvisa.business.infra.report.ReportGenerator;
import com.worldvisa.business.infra.utils.BasicUtils;
import com.worldvisa.business.infra.utils.Mailer;
import com.worldvisa.business.service.stub.MarketService;
import com.worldvisa.business.service.stub.ProfileService;
import com.worldvisa.business.service.stub.WorkflowService;

/**
 * @author Administrator
 */
@RemoteProxy
public class ListContactsView
extends BaseView {
    private static final long serialVersionUID = 5882116114765277848L;
    @Autowired
    private Mailer            mailer;
    @Autowired
    private MarketService     marketService;
    @Autowired
    private ProfileService    profileService;
    @Autowired
    private ReportGenerator   reportGenerator;
    @Autowired
    private WorkflowService   workflowService;

    @RemoteMethod
    public DWRResponse<Notification> addNotification(final Notification notification)
    throws Exception {
        final DWRResponse<Notification> response = new DWRResponse<Notification>();
        final UserComment status = new UserComment();
        status.setDescription(notification.getNotificationComment());
        status.setSubstatus(LookupConstants.CASE_SUBSTATUS.Miscellaneous);
        status.setUserId(new Long(notification.getByUserId().split(":")[0]));
        this.marketService.createStatus(this.user(), status);
        notification.setNotificationComment(notification.getByUserId().split(":")[1] + " : " + notification.getNotificationComment());
        notification.setByUserId(this.user().getEmailId());
        notification.setNotificationStatus(LookupConstants.NOTIFICATION_STATUS.Created);
        notification.setCreationTime(BasicUtils.getCurrentDate());
        this.workflowService.createNotification(notification);
        response.setMainObject(notification);
        response.addMessage(new WebMessage("Notification Added Successfully", MessageSeverity.INFO));
        this.profileService.createPublicDart(this.user(), "Updated market user status : " + notification.getNotificationComment(),
        notification.getNotificationStatus(), null);
        return response;
    }

    @RemoteMethod
    public List<String> autoCompleteDataGroup(final String partialName) {
        final List<String> list = new ArrayList<String>();
        try {
            final UserGroupFilter filter = new UserGroupFilter();
            filter.setUserGroupName(partialName);
            final List<UserGroup> userGroupList = this.marketService.readUserGroupList(filter);
            for (final UserGroup userGroup : userGroupList) {
                list.add(userGroup.getUserGroupName());
            }
        } catch (final Exception e) {
            // do nothing here
        }
        return list;
    }

    private DWRResponse<Boolean> communicate(final List<User> userList, final List<Communication> communicationsList)
    throws Exception {
        final DWRResponse<Boolean> response = new DWRResponse<Boolean>();
        List<User> mailedUserList;
        List<FileBean> attatchments;
        int failureCount = 0;
        for (final Communication communication : communicationsList) {
            attatchments = new ArrayList<FileBean>();
            if (communication.getAttachments() != null) {
                for (final FileDetails file : communication.getAttachments()) {
                    attatchments.add(file.getFile());
                }
            }
            mailedUserList = new ArrayList<User>();
            for (final User user : userList) {
                try {
                    if (user.getEmail() != null && user.getEmail().trim().length() != 0
                    && LookupConstants.ACTION_TYPE.Mailer.equals(communication.getActionType())) {
                        this.mailer.sendEmail(communication.getMailSubject(),
                        "<div style=\"font-size:12px;font-family:Verdana, Geneva, sans-serif;\">" + communication.getMailBody() + "</div>",
                        user.getEmail(), attatchments, false);
                    } else if (user.getMobile() != null && user.getMobile().trim().length() != 0
                    && LookupConstants.ACTION_TYPE.SMS.equals(communication.getActionType())) {
                        // TODO: code sms sender here
                    }
                    mailedUserList.add(user);
                } catch (final Exception e) {
                    BaseView.log.error(e);
                    failureCount++;
                }
            }
            this.marketService.updateMailer(this.user(), communication, mailedUserList);
        }
        if (failureCount == 0) {
            this.profileService.createPublicDart(this.user(), "Scheduled mails from market: " + (userList.size() * communicationsList.size())
            + " mails scheduled successfully.", LookupConstants.CASE_SUBSTATUS.Miscellaneous, null);
            response
            .addMessage(new WebMessage((userList.size() * communicationsList.size()) + " mails scheduled successfully.", MessageSeverity.INFO));
        } else {
            this.profileService.createPublicDart(this.user(), "Scheduled mails from market: " + "System could not schedule " + failureCount
            + " communications. " + (userList.size() * communicationsList.size() - failureCount) + " communications were successfully schedule.",
            LookupConstants.CASE_SUBSTATUS.Miscellaneous, null);
            response.addMessage(new WebMessage("System could not schedule " + failureCount + " communications. "
            + (userList.size() * communicationsList.size() - failureCount) + " communications were successfully schedule.", MessageSeverity.WARN));
        }
        return response;
    }

    @RemoteMethod
    public DWRResponse<Boolean> deleteUsers(final List<Long> userIdList)
    throws Exception {
        final DWRResponse<Boolean> response = new DWRResponse<Boolean>();
        this.marketService.deleteUsers(userIdList);
        response.setMainObject(true);
        response.addMessage(new WebMessage(userIdList.size() + " user(s) deleted successfully", MessageSeverity.INFO));
        this.profileService.createPublicDart(this.user(), "Deleted " + userIdList.size() + " user(s) from market",
        LookupConstants.CASE_SUBSTATUS.Miscellaneous, null);
        return response;
    }

    @RemoteMethod
    public FileTransfer exportToExcel(final UserFilter filter)
    throws Exception {
        filter.setupDefaults();
        // creating and streaming file to server
        return new FileTransfer("NonRegUserList.xls", "application/ms-excel", new FileInputStream(this.generateReport(ReportType.XLS,
        "NonRegUserList.xls", this.marketService.searchNonRegUsers(filter), null, this.reportGenerator)));
    }

    @RemoteMethod
    public List<UserGroup> getAllUserGroups()
    throws Exception {
        final UserGroupFilter filter = new UserGroupFilter();
        filter.setupDefaults();
        return this.marketService.readUserGroupList(filter);
    }

    @RemoteMethod
    public List<User> getContacts(final UserFilter filter)
    throws Exception {
        return this.marketService.searchNonRegUsers(filter);
    }

    @RemoteMethod
    public Integer getContactsLength(final UserFilter filter)
    throws Exception {
        return this.marketService.searchNonRegUsersCount(filter);
    }

    @RemoteMethod
    public DWRResponse<Boolean> sendCommunications(final List<Long> userIds, final List<Long> communicationIds)
    throws Exception {
        return this.communicate(this.marketService.readUserList(userIds), this.marketService.readCommunications(communicationIds));
    }

    @RemoteMethod
    public DWRResponse<Boolean> sendCommunicationsToAll(final UserFilter filter, final List<Long> communicationIds)
    throws Exception {
        filter.setupDefaults();
        return this.communicate(this.getContacts(filter), this.marketService.readCommunications(communicationIds));
    }

    @RemoteMethod
    public List<UserComment> showFollowupHistory(final User user)
    throws Exception {
        return this.marketService.readFollowupHistory(user);
    }

    @RemoteMethod
    public DWRResponse<User> updateUserDetails(final User user)
    throws Exception {
        final DWRResponse<User> response = new DWRResponse<User>();
        this.marketService.updateUserDetails(user);
        response.setMainObject(user);
        response.addMessage(new WebMessage("User details updated successfully", MessageSeverity.INFO));
        return response;
    }
}
