/******************************************************************************
 * Name : MarketServiceImpl.java Author : Administrator Date : 09-May-2010,2:27:34 AM Description : Implementation for MarketServiceImpl
 *****************************************************************************/
package com.worldvisa.business.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.worldvisa.business.dao.market.CommunicationDAO;
import com.worldvisa.business.dao.market.FileDetailsDAO;
import com.worldvisa.business.dao.market.UserCommentDAO;
import com.worldvisa.business.dao.market.UserDAO;
import com.worldvisa.business.dao.market.UserGroupCommentDAO;
import com.worldvisa.business.dao.market.UserGroupDAO;
import com.worldvisa.business.dao.request.CaseStatusDAO;
import com.worldvisa.business.dao.workflow.ConversationDAO;
import com.worldvisa.business.domain.common.FileBean;
import com.worldvisa.business.domain.market.Communication;
import com.worldvisa.business.domain.market.CommunicationsFilter;
import com.worldvisa.business.domain.market.FileDetails;
import com.worldvisa.business.domain.market.User;
import com.worldvisa.business.domain.market.UserComment;
import com.worldvisa.business.domain.market.UserFilter;
import com.worldvisa.business.domain.market.UserGroup;
import com.worldvisa.business.domain.market.UserGroupComment;
import com.worldvisa.business.domain.market.UserGroupFilter;
import com.worldvisa.business.domain.profile.LoginDetails;
import com.worldvisa.business.domain.request.CaseStatus;
import com.worldvisa.business.domain.workflow.Conversation;
import com.worldvisa.business.infra.base.BaseServiceImpl;
import com.worldvisa.business.infra.constants.LookupConstants;
import com.worldvisa.business.infra.utils.BasicUtils;
import com.worldvisa.business.service.stub.FileHandlerService;
import com.worldvisa.business.service.stub.MarketService;

/**
 * 
 */
@Service
public class MarketServiceImpl
extends BaseServiceImpl
implements MarketService {
    @Autowired
    private CaseStatusDAO       caseStatusDAO;
    @Autowired
    private CommunicationDAO    communicationDAO;
    @Autowired
    private ConversationDAO     conversationDAO;
    @Autowired
    private FileDetailsDAO      fileDetailsDAO;
    @Autowired
    private FileHandlerService  fileHandlerService;
    @Autowired
    private UserCommentDAO      userCommentDAO;
    @Autowired
    private UserDAO             userDAO;
    @Autowired
    private UserGroupCommentDAO userGroupCommentDAO;
    @Autowired
    private UserGroupDAO        userGroupDAO;

    /**
     * @see com.worldvisa.business.service.stub.MarketService#createCommunication(com.worldvisa.business.domain.market.Communication)
     */
    @Transactional(rollbackFor = java.lang.Exception.class)
    public Communication createCommunication(final Communication newCommunication)
    throws Exception {
        try {
            if (newCommunication.getSeqNum() == null) {
                newCommunication.setSentTime(BasicUtils.getCurrentDateTime());
                newCommunication.setSeqNum((Long) this.communicationDAO.create(newCommunication));
            } else {
                this.fileDetailsDAO.delete(new FileDetails(null, newCommunication.getSeqNum()));
            }
            final List<FileBean> uploadedFileList = new ArrayList<FileBean>();
            for (final FileDetails details : newCommunication.getAttachments()) {
                details.setMailId(newCommunication.getSeqNum());
                final String prevFilePath = details.getFile().getRelativePath();
                details.setFileId((Long) this.fileDetailsDAO.create(details));
                details.getFile().setRelativePath(
                "MARKET_FILE_DETAILS/" + newCommunication.getSeqNum() + "/" + details.getFileId() + "." + details.getFile().getFileName());
                this.fileHandlerService.updateFile(prevFilePath, details.getFile().getRelativePath());
                newCommunication.setMailBody(newCommunication.getMailBody().replaceAll(prevFilePath, details.getFile().getRelativePath()));
                uploadedFileList.add(details.getFile());
            }
            this.fileHandlerService.cleanFolder("MARKET_FILE_DETAILS/" + newCommunication.getSeqNum(), uploadedFileList);
            // update communications after renaming / resetting the files
            final Communication filter = new Communication();
            filter.setSeqNum(newCommunication.getSeqNum());
            this.communicationDAO.update(newCommunication, filter);
            return newCommunication;
        } catch (final Exception e) {
            final Exception ex = new Exception("Mail Template could not be saved. Please contact administrator for more details.");
            ex.setStackTrace(e.getStackTrace());
            throw ex;
        }
    }

    /**
     * @see com.worldvisa.business.service.stub.MarketService#createDataGroupComments(com.worldvisa.business.domain.market.UserGroupComment)
     */
    public void createDataGroupComments(final UserGroupComment userGroupComment)
    throws Exception {
        this.userGroupCommentDAO.create(userGroupComment);
    }

    public UserGroupComment createGroupStatus(LoginDetails user, UserGroupComment userGroupComment)
    throws Exception {
        userGroupComment.setAddedBy(user.getEmailId());
        userGroupComment.setAddedOn(BasicUtils.getCurrentDateTime());
        userGroupComment.setCommentId((Long) this.userGroupCommentDAO.create(userGroupComment));
        return userGroupComment;
    }

    /**
     * @see com.worldvisa.business.service.stub.MarketService#createNonRegUsers(java.util.List)
     */
    public User createNonRegUsers(final User user)
    throws Exception {
        this.userDAO.create(user);
        return user;
    }

    /**
     * @see com.worldvisa.business.service.stub.MarketService#addStatus(com.worldvisa.business.domain.market.UserComment)
     */
    @Transactional(rollbackFor = java.lang.Exception.class)
    public void createStatus(final LoginDetails currentUser, final UserComment newFollowupStatus)
    throws Exception {
        try {
            newFollowupStatus.setAddedOn(BasicUtils.getCurrentDateTime());
            newFollowupStatus.setAddedBy(currentUser.getEmailId());
            this.userCommentDAO.create(newFollowupStatus);
        } catch (final Exception e) {
            final Exception ex = new Exception(
            "The system could not save the case status. Try adding the case status again. If the problem persists, contact administrator for assistance.");
            ex.setStackTrace(e.getStackTrace());
            throw ex;
        }
    }

    public UserGroup createUserGroup(UserGroup userGroup, final LoginDetails currUserDetails)
    throws Exception {
        userGroup.setAddedBy(currUserDetails.getEmailId());
        userGroup.setAddedOn(BasicUtils.getCurrentDateTime());
        userGroup.setUserGroupId((Long) this.userGroupDAO.create(userGroup));
        return userGroup;
    }

    /**
     * @see com.worldvisa.business.service.stub.MarketService#deleteCommunication(com.worldvisa.business.domain.market.Communication)
     */
    public void deleteCommunication(final Communication filter)
    throws Exception {
        final UserGroupComment userGroupComment = new UserGroupComment();
        userGroupComment.setCommunicationId(filter.getSeqNum());
        this.userGroupCommentDAO.delete(userGroupComment);
        final UserComment userComment = new UserComment();
        userComment.setCommunicationId(filter.getSeqNum());
        this.userCommentDAO.delete(userComment);
        this.fileDetailsDAO.delete(new FileDetails(null, filter.getSeqNum()));
        this.communicationDAO.delete(filter);
        this.fileHandlerService.deleteRecursive("MARKET_FILE_DETAILS/" + filter.getSeqNum());
    }

    public UserGroup deleteUserGroup(UserGroup userGroup)
    throws Exception {
        final UserGroupComment comment = new UserGroupComment();
        comment.setUserGroupId(userGroup.getUserGroupId());
        this.userGroupCommentDAO.delete(comment);
        final User user = new User();
        user.setUserGroupId(userGroup.getUserGroupId());
        this.userDAO.delete(user);
        this.userGroupDAO.delete(userGroup);
        return userGroup;
    }

    /**
     * @see com.worldvisa.business.service.stub.MarketService#deleteUsers(java.util.List)
     */
    public void deleteUsers(final List<Long> userIdList)
    throws Exception {
        this.userCommentDAO.deleteByUserId(userIdList);
        this.userDAO.deleteByUserId(userIdList);
    }

    /**
     * @see com.worldvisa.business.service.stub.MarketService#readCommunication(com.worldvisa.business.domain.market.Communication)
     */
    public Communication readCommunication(final Communication newCommunication)
    throws Exception {
        final Communication communication = this.communicationDAO.read(newCommunication);
        final FileDetails attachment = new FileDetails(null, communication.getSeqNum());
        communication.setAttachments(this.fileDetailsDAO.readList(attachment));
        return communication;
    }

    /**
     * @see com.worldvisa.business.service.stub.MarketService#readCommunicationList(com.worldvisa.business.domain.market.Communication)
     */
    public List<Communication> readCommunicationList(final CommunicationsFilter filter)
    throws Exception {
        return this.communicationDAO.readSearchResults(filter);
    }

    /**
     * @see com.worldvisa.business.service.stub.MarketService#readCommunicationListCount(com.worldvisa.business.domain.market.CommunicationsFilter)
     */
    public Integer readCommunicationListCount(final CommunicationsFilter filter)
    throws Exception {
        return this.communicationDAO.readSearchResultsCount(filter);
    }

    /**
     * @see com.worldvisa.business.service.stub.MarketService#readCommunications(java.util.List)
     */
    public List<Communication> readCommunications(final List<Long> communicationIds)
    throws Exception {
        final List<Communication> communicationList = this.communicationDAO.readByCommunicationIds(communicationIds);
        FileDetails attachment;
        for (final Communication communication : communicationList) {
            attachment = new FileDetails(null, communication.getSeqNum());
            communication.setAttachments(this.fileDetailsDAO.readList(attachment));
        }
        return communicationList;
    }

    /**
     * @see com.worldvisa.business.service.stub.MarketService#readFollowupHistory(com.worldvisa.business.domain.market.User)
     */
    public List<UserComment> readFollowupHistory(final User user)
    throws Exception {
        if (user.getRequestNumber() == null) {
            final List<UserComment> userComments = this.userCommentDAO.readList(new UserComment(null, user.getUserId()));
            final List<UserGroupComment> groupComments = this.userGroupCommentDAO.readList(new UserGroupComment(null, user.getUserGroupId()));
            for (final UserGroupComment groupComment : groupComments) {
                userComments.add(groupComment.translateToUserComment(user.getUserId()));
            }
            for (final UserComment userComment : userComments) {
                if (null != userComment.getCommunicationId()) {
                    userComment.setCommunication(this.communicationDAO.read(new Communication(userComment.getCommunicationId())));
                }
            }
            Collections.sort(userComments);
            return userComments;
        } else {
            final List<CaseStatus> caseStatusList = this.caseStatusDAO.readList(new CaseStatus(null, user.getRequestNumber()));
            final List<UserComment> userComment = new ArrayList<UserComment>();
            for (final CaseStatus caseStatus : caseStatusList) {
                final UserComment status = new UserComment();
                status.setAddedOn(caseStatus.getAddedDate());
                status.setDescription(caseStatus.getDescription());
                status.setAddedBy(caseStatus.getSetBy());
                status.setSubstatus(caseStatus.getSubstatus());
                userComment.add(status);
            }
            return userComment;
        }
    }

    /**
     * @see com.worldvisa.business.service.stub.MarketService#readGroupComments(com.worldvisa.business.domain.market.UserGroup)
     */
    public List<UserGroupComment> readGroupComments(final UserGroup userGroup)
    throws Exception {
        final UserGroupComment comments = new UserGroupComment();
        comments.setUserGroupId(userGroup.getUserGroupId());
        final List<UserGroupComment> groupComments = this.userGroupCommentDAO.readList(comments);
        for (final UserGroupComment comment : groupComments) {
            if (null != comment.getCommunicationId()) {
                comment.setCommunication(this.communicationDAO.read(new Communication(comment.getCommunicationId())));
            }
        }
        return groupComments;
    }

    /**
     * @see com.worldvisa.business.service.stub.MarketService#readUserGroupList(com.worldvisa.business.domain.market.UserGroupFilter)
     */
    public List<UserGroup> readUserGroupList(UserGroupFilter filter)
    throws Exception {
        return this.userGroupDAO.readListByFiter(filter);
    }

    /**
     * @see com.worldvisa.business.service.stub.MarketService#readUserGroupListCount(com.worldvisa.business.domain.market.UserGroupFilter)
     */
    public Integer readUserGroupListCount(UserGroupFilter filter)
    throws Exception {
        return this.userGroupDAO.readListSizeByFiter(filter);
    }

    /**
     * @see com.worldvisa.business.service.stub.MarketService#readUserList(java.util.List)
     */
    public List<User> readUserList(final List<Long> userIds)
    throws Exception {
        return this.userDAO.readByUserIds(userIds);
    }

    public List<User> readUserListByGroupId(Long userGroupId)
    throws Exception {
        final User filter = new User();
        filter.setUserGroupId(userGroupId);
        return this.userDAO.readList(filter);
    }

    /**
     * @see com.worldvisa.business.service.stub.MarketService#searchNonRegUsers(com.worldvisa.business.domain.market.User)
     */
    public List<User> searchNonRegUsers(final UserFilter filter)
    throws Exception {
        return this.userDAO.readByFilter(filter);
    }

    /**
     * @see com.worldvisa.business.service.stub.MarketService#searchNonRegUsersCount(com.worldvisa.business.domain.market.UserFilter)
     */
    public Integer searchNonRegUsersCount(final UserFilter filter)
    throws Exception {
        return this.userDAO.readCountByFilter(filter);
    }

    /**
     * @see com.worldvisa.business.service.stub.MarketService#updateMailer(com.worldvisa.business.domain.market.Communication, java.util.List)
     */
    public void updateMailer(final LoginDetails curentUser, final Communication communication, final List<User> nonRegUserList)
    throws Exception {
        UserComment userComment;
        for (final User user : nonRegUserList) {
            if (user.getRequestNumber() == null) {
                userComment = new UserComment();
                userComment.setCommunicationId(communication.getSeqNum());
                userComment.setUserId(user.getUserId());
                userComment.setAddedOn(BasicUtils.getCurrentDateTime());
                userComment.setDescription("Communication Sent.");
                userComment.setAddedBy(curentUser.getEmailId());
                userComment.setSubstatus(LookupConstants.CASE_SUBSTATUS.Miscellaneous);
                this.userCommentDAO.create(userComment);
            } else {
                final Conversation newConversation = new Conversation();
                newConversation.setReportId(user.getRequestNumber());
                newConversation.setAttatchments(new ArrayList<Long>());
                newConversation.setMailSubject(communication.getMailSubject());
                newConversation.setRemarks(communication.getMailBody());
                newConversation.setSentBy(curentUser.getEmailId());
                newConversation.setReceivedBy(user.getEmail());
                newConversation.setTimestamp(BasicUtils.getCurrentDateTime());
                newConversation.setDraftInd(0L);
                this.conversationDAO.create(newConversation);
            }
        }
    }

    public void updateMailer(LoginDetails curentUser, Communication communication, Long userGroupId)
    throws Exception {
        final UserGroupComment userGroupComment = new UserGroupComment();
        userGroupComment.setCommunicationId(communication.getSeqNum());
        userGroupComment.setUserGroupId(userGroupId);
        userGroupComment.setAddedOn(BasicUtils.getCurrentDateTime());
        userGroupComment.setDescription("Communication Sent.");
        userGroupComment.setAddedBy(curentUser.getEmailId());
        userGroupComment.setSubstatus(LookupConstants.CASE_SUBSTATUS.Miscellaneous);
        this.userGroupCommentDAO.create(userGroupComment);
    }

    /**
     * @see com.worldvisa.business.service.stub.MarketService#updateUserDetails(com.worldvisa.business.domain.market.User)
     */
    public void updateUserDetails(final User currUserDetails)
    throws Exception {
        this.userDAO.update(currUserDetails, new User(currUserDetails.getUserId()));
    }
}
