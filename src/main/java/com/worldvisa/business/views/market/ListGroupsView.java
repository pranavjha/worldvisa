/**
 * 
 */
package com.worldvisa.business.views.market;

import java.util.ArrayList;
import java.util.List;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import com.worldvisa.business.domain.common.DWRResponse;
import com.worldvisa.business.domain.common.FileBean;
import com.worldvisa.business.domain.common.WebMessage;
import com.worldvisa.business.domain.market.Communication;
import com.worldvisa.business.domain.market.FileDetails;
import com.worldvisa.business.domain.market.User;
import com.worldvisa.business.domain.market.UserGroup;
import com.worldvisa.business.domain.market.UserGroupComment;
import com.worldvisa.business.domain.market.UserGroupFilter;
import com.worldvisa.business.infra.base.BaseView;
import com.worldvisa.business.infra.constants.LookupConstants;
import com.worldvisa.business.infra.constants.MessageSeverity;
import com.worldvisa.business.infra.utils.BasicUtils;
import com.worldvisa.business.infra.utils.Mailer;
import com.worldvisa.business.service.stub.MarketService;
import com.worldvisa.business.service.stub.ProfileService;

/**
 * @author Administrator
 */
@RemoteProxy
public class ListGroupsView
extends BaseView {
    private static final long serialVersionUID = 5882116114765277848L;
    @Autowired
    private Mailer            mailer;
    @Autowired
    private MarketService     marketService;
    @Autowired
    private ProfileService    profileService;

    @RemoteMethod
    public DWRResponse<UserGroupComment> addStatus(UserGroupComment groupComments)
    throws Exception {
        final DWRResponse<UserGroupComment> response = new DWRResponse<UserGroupComment>();
        groupComments.setAddedBy(this.user().getEmailId());
        groupComments.setAddedOn(BasicUtils.getCurrentDateTime());
        this.marketService.createDataGroupComments(groupComments);
        response.setMainObject(groupComments);
        response.addMessage(new WebMessage("Status added successfully", MessageSeverity.INFO));
        this.profileService.createPublicDart(this.user(), "Updated Status for user group id " + groupComments.getUserGroupId() + " : "
        + groupComments.getDescription(), LookupConstants.CASE_SUBSTATUS.Miscellaneous, null);
        return response;
    }

    @RemoteMethod
    public DWRResponse<UserGroup> deleteGroup(UserGroup userGroup)
    throws Exception {
        final DWRResponse<UserGroup> response = new DWRResponse<UserGroup>();
        response.setMainObject(this.marketService.deleteUserGroup(userGroup));
        response.addMessage(new WebMessage("User Group deleted successfully", MessageSeverity.INFO));
        this.profileService.createPublicDart(this.user(), "Deleted Group ID: " + userGroup.getUserGroupId(),
        LookupConstants.CASE_SUBSTATUS.Miscellaneous, null);
        return response;
    }

    @RemoteMethod
    public List<UserGroup> listUserGroups(UserGroupFilter filter)
    throws Exception {
        return this.marketService.readUserGroupList(filter);
    }

    @RemoteMethod
    public Integer listUserGroupsCount(UserGroupFilter filter)
    throws Exception {
        return this.marketService.readUserGroupListCount(filter);
    }

    @RemoteMethod
    public DWRResponse<Boolean> sendCommunications(final Long userGroupId, final List<Long> communicationIds)
    throws Exception {
        final List<User> userList = this.marketService.readUserListByGroupId(userGroupId);
        final List<Communication> communicationsList = this.marketService.readCommunications(communicationIds);
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
                        System.out.println("mailing: " + user.getEmail() + "sub: " + communication.getMailSubject());
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
            this.marketService.updateMailer(this.user(), communication, userGroupId);
        }
        if (failureCount == 0) {
            response.addMessage(new WebMessage("All mails scheduled Successfully", MessageSeverity.INFO));
        } else {
            response.addMessage(new WebMessage("System could not schedule " + failureCount + " communications. "
            + (userList.size() * communicationsList.size() - failureCount) + " communications were successfully schedule.", MessageSeverity.WARN));
        }
        this.profileService.createPublicDart(this.user(), "Scheduled mails for group id : " + userGroupId,
        LookupConstants.CASE_SUBSTATUS.Miscellaneous, null);
        return response;
    }

    @RemoteMethod
    public List<UserGroupComment> showFollowupHistory(final UserGroup userGroup)
    throws Exception {
        return this.marketService.readGroupComments(userGroup);
    }
}
