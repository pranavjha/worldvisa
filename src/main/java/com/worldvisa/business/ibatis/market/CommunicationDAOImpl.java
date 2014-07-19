/******************************************************************************
 * Name : UserDetailsDAOImpl.java Author : Administrator Date : 2010/05/08 Description : Implementation for the UserDetailsDAOImpl class
 *****************************************************************************/
package com.worldvisa.business.ibatis.market;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.worldvisa.business.dao.market.CommunicationDAO;
import com.worldvisa.business.domain.market.Communication;
import com.worldvisa.business.domain.market.CommunicationsFilter;
import com.worldvisa.business.infra.base.BaseIbatisDAOImpl;

/**
 * iBatis DAO implementation for interfacing with the ADMIN.PROFILE_USER_DETAILS table<br/>
 */
@Repository("marketCommunicationDAOImpl")
public class CommunicationDAOImpl
extends BaseIbatisDAOImpl<Communication>
implements CommunicationDAO {
    /**
     * @see com.worldvisa.business.dao.market.CommunicationDAO#readByCommunicationIds(java.util.List)
     */
    @SuppressWarnings("unchecked")
    public List<Communication> readByCommunicationIds(final List<Long> communicationIds)
    throws Exception {
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", communicationIds);
        return this.getSqlMapClientTemplate().queryForList("marketCommunication.readByCommunicationIds", map);
    }

    @SuppressWarnings("unchecked")
    public List<Communication> readSearchResults(final CommunicationsFilter filter)
    throws Exception {
        return this.getSqlMapClientTemplate().queryForList("marketCommunication.readSearchResults", filter);
    }

    /**
     * @see com.worldvisa.business.dao.market.CommunicationDAO#readSearchResultsCount(com.worldvisa.business.domain.market.CommunicationsFilter)
     */
    public Integer readSearchResultsCount(final CommunicationsFilter filter)
    throws Exception {
        return (Integer) this.getSqlMapClientTemplate().queryForObject("marketCommunication.readSearchResultsCount", filter);
    }
}
