/******************************************************************************
 * Name : FollowupStatusDAO.java Author : Administrator Date : 2010/05/08 Description : Implementation for the FollowupStatusDAO interface
 *****************************************************************************/
package com.worldvisa.business.dao.market;

import java.util.List;
import com.worldvisa.business.domain.market.UserGroup;
import com.worldvisa.business.domain.market.UserGroupFilter;
import com.worldvisa.business.infra.base.BaseIbatisDAO;

/**
 * DAO interface for interfacing with the MARKET_USER_GROUP table<br/>
 */
public interface UserGroupDAO
extends BaseIbatisDAO<UserGroup> {
    /**
     * @param filter
     * @return
     */
    public List<UserGroup> readListByFiter(UserGroupFilter filter)
    throws Exception;

    /**
     * @param filter
     * @return
     */
    public Integer readListSizeByFiter(UserGroupFilter filter)
    throws Exception;
}
