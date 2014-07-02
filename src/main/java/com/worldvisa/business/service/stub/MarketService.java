/******************************************************************************
 * Name : MarketServiceImpl.java Author : Administrator Date : 09-May-2010,2:27:34 AM Description : Implementation for MarketServiceImpl
 *****************************************************************************/
package com.worldvisa.business.service.stub;

import java.util.List;
import com.worldvisa.business.domain.market.Communication;
import com.worldvisa.business.domain.market.CommunicationsFilter;
import com.worldvisa.business.domain.market.User;
import com.worldvisa.business.domain.market.UserComment;
import com.worldvisa.business.domain.market.UserFilter;
import com.worldvisa.business.domain.market.UserGroup;
import com.worldvisa.business.domain.market.UserGroupComment;
import com.worldvisa.business.domain.market.UserGroupFilter;
import com.worldvisa.business.domain.profile.LoginDetails;

/**
 * 
 */
public interface MarketService {
    public Communication createCommunication(Communication newCommunication)
    throws Exception;

    public void createDataGroupComments(UserGroupComment userGroupComment)
    throws Exception;

    public UserGroupComment createGroupStatus(LoginDetails user, UserGroupComment userGroupComment)
    throws Exception;

    public User createNonRegUsers(User user)
    throws Exception;

    public void createStatus(LoginDetails currentUser, UserComment newFollowupStatus)
    throws Exception;

    public UserGroup createUserGroup(UserGroup userGroup, LoginDetails currUserDetails)
    throws Exception;

    public void deleteCommunication(Communication filter)
    throws Exception;

    public UserGroup deleteUserGroup(UserGroup userGroup)
    throws Exception;

    public void deleteUsers(List<Long> userIdList)
    throws Exception;

    public Communication readCommunication(Communication newCommunication)
    throws Exception;

    public List<Communication> readCommunicationList(CommunicationsFilter filter)
    throws Exception;

    public Integer readCommunicationListCount(CommunicationsFilter filter)
    throws Exception;

    public List<Communication> readCommunications(List<Long> communicationIds)
    throws Exception;

    public List<UserComment> readFollowupHistory(User user)
    throws Exception;

    public List<UserGroupComment> readGroupComments(UserGroup userGroup)
    throws Exception;

    public List<UserGroup> readUserGroupList(UserGroupFilter filter)
    throws Exception;

    public Integer readUserGroupListCount(UserGroupFilter filter)
    throws Exception;

    public List<User> readUserList(List<Long> userIds)
    throws Exception;

    public List<User> readUserListByGroupId(Long userGroupId)
    throws Exception;

    public List<User> searchNonRegUsers(UserFilter filter)
    throws Exception;

    public Integer searchNonRegUsersCount(UserFilter filter)
    throws Exception;

    public void updateMailer(LoginDetails curentUser, Communication communication, List<User> nonRegUserList)
    throws Exception;

    public void updateMailer(LoginDetails curentUser, Communication communication, Long userGroupId)
    throws Exception;

    public void updateUserDetails(User currUserDetails)
    throws Exception;
}
