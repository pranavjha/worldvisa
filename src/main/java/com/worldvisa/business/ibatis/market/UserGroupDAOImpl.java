/******************************************************************************
 * Name : DataGroupCommentsDAOImpl.java Author : Administrator Date : 2010/05/08 Description : Implementation for the DataGroupCommentsDAOImpl class
 *****************************************************************************/
package com.worldvisa.business.ibatis.market;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.worldvisa.business.dao.market.UserGroupDAO;
import com.worldvisa.business.domain.market.UserGroup;
import com.worldvisa.business.domain.market.UserGroupFilter;
import com.worldvisa.business.infra.base.BaseIbatisDAOImpl;

/**
 * iBatis DAO implementation for interfacing with the ADMIN.MARKET_FOLLOWUP_STATUS table<br/>
 */
@Repository("marketUserGroupDAOImpl")
public class UserGroupDAOImpl
extends BaseIbatisDAOImpl<UserGroup>
implements UserGroupDAO {
    /**
     * @see com.worldvisa.business.dao.market.UserGroupDAO#readListByFiter(com.worldvisa.business.domain.market.UserGroupFilter)
     */
    @SuppressWarnings("unchecked")
    public List<UserGroup> readListByFiter(UserGroupFilter filter)
    throws Exception {
        return this.getSqlMapClientTemplate().queryForList("marketUserGroup.readByFilter", filter);
    }

    /**
     * @see com.worldvisa.business.dao.market.UserGroupDAO#readListSizeByFiter(com.worldvisa.business.domain.market.UserGroupFilter)
     */
    public Integer readListSizeByFiter(UserGroupFilter filter)
    throws Exception {
        return (Integer) this.getSqlMapClientTemplate().queryForObject("marketUserGroup.readCountByFilter", filter);
    }
}
