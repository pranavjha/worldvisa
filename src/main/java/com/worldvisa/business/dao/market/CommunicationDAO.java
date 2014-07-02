/******************************************************************************
 * Name : UserDetailsDAO.java Author : Administrator Date : 2010/05/08 Description : Implementation for the UserDetailsDAO interface
 *****************************************************************************/
package com.worldvisa.business.dao.market;

import java.util.List;
import com.worldvisa.business.domain.market.Communication;
import com.worldvisa.business.domain.market.CommunicationsFilter;
import com.worldvisa.business.infra.base.BaseIbatisDAO;

/**
 * DAO interface for interfacing with the PROFILE_USER_DETAILS table<br/>
 */
public interface CommunicationDAO
extends BaseIbatisDAO<Communication> {
    /**
     * @param communicationIds
     * @return
     */
    public List<Communication> readByCommunicationIds(List<Long> communicationIds)
    throws Exception;

    public List<Communication> readSearchResults(CommunicationsFilter filter)
    throws Exception;

    /**
     * @param filter
     * @return
     */
    public Integer readSearchResultsCount(CommunicationsFilter filter)
    throws Exception;
}
