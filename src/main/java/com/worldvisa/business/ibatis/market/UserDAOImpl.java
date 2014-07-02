/******************************************************************************
 * Name : UserDetailsDAOImpl.java Author : Administrator Date : 2010/05/08 Description : Implementation for the UserDetailsDAOImpl class
 *****************************************************************************/
package com.worldvisa.business.ibatis.market;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.worldvisa.business.dao.market.UserDAO;
import com.worldvisa.business.domain.market.User;
import com.worldvisa.business.domain.market.UserFilter;
import com.worldvisa.business.infra.base.BaseIbatisDAOImpl;

/**
 * iBatis DAO implementation for interfacing with the ADMIN.PROFILE_USER_DETAILS table<br/>
 */
@Repository("marketUserDAOImpl")
public class UserDAOImpl
extends BaseIbatisDAOImpl<User>
implements UserDAO {
    /**
     * @see com.worldvisa.business.dao.market.UserDAO#deleteByUserId(java.util.List)
     */
    public void deleteByUserId(final List<Long> userIdList)
    throws Exception {
        final Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("user", userIdList);
        this.getSqlMapClientTemplate().delete("marketUser.deleteByUserId", paramMap);
    }

    /**
     * @see com.worldvisa.business.dao.market.UserDAO#readByFilter(com.worldvisa.business.domain.market.User)
     */
    @SuppressWarnings("unchecked")
    public List<User> readByFilter(final UserFilter filter)
    throws Exception {
        return this.getSqlMapClientTemplate().queryForList("marketUser.readByFilter", filter);
    }

    /**
     * @see com.worldvisa.business.dao.market.UserDAO#readByUserIds(java.util.List)
     */
    @SuppressWarnings("unchecked")
    public List<User> readByUserIds(final List<Long> userIds)
    throws Exception {
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", userIds);
        return this.getSqlMapClientTemplate().queryForList("marketUser.readByUserIds", map);
    }

    /**
     * @see com.worldvisa.business.dao.market.UserDAO#readCountByFilter(com.worldvisa.business.domain.market.UserFilter)
     */
    public Integer readCountByFilter(final UserFilter filter)
    throws Exception {
        return (Integer) this.getSqlMapClientTemplate().queryForObject("marketUser.readCountByFilter", filter);
    }
}