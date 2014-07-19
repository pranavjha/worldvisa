/******************************************************************************
 * Name : UserDetailsDAO.java Author : Administrator Date : 2010/05/08 Description : Implementation for the UserDetailsDAO interface
 *****************************************************************************/
package com.worldvisa.business.dao.market;

import java.util.List;
import com.worldvisa.business.domain.market.User;
import com.worldvisa.business.domain.market.UserFilter;
import com.worldvisa.business.infra.base.BaseIbatisDAO;

/**
 * DAO interface for interfacing with the PROFILE_USER_DETAILS table<br/>
 */
public interface UserDAO
extends BaseIbatisDAO<User> {
    /**
     * @param userIdList
     */
    public void deleteByUserId(List<Long> userIdList)
    throws Exception;

    public List<User> readByFilter(UserFilter filter)
    throws Exception;

    /**
     * @param userIds
     * @return
     */
    public List<User> readByUserIds(List<Long> userIds)
    throws Exception;

    /**
     * @param filter
     * @return
     */
    public Integer readCountByFilter(UserFilter filter)
    throws Exception;
}
