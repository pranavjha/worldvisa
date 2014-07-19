/******************************************************************************
 * Name : RelativesAbroadDAOImpl.java Author : Administrator Date : 2010/05/08 Description : Implementation for the RelativesAbroadDAOImpl class
 *****************************************************************************/
package com.worldvisa.business.ibatis.request;

import org.springframework.stereotype.Repository;
import com.worldvisa.business.dao.request.RelativesAbroadDAO;
import com.worldvisa.business.domain.request.RelativesAbroad;
import com.worldvisa.business.infra.base.BaseIbatisDAOImpl;

/**
 * iBatis DAO implementation for interfacing with the ADMIN.REQUEST_RELATIVES_ABROAD table<br/>
 */
@Repository("requestRelativesAbroadDAOImpl")
public class RelativesAbroadDAOImpl
extends BaseIbatisDAOImpl<RelativesAbroad>
implements RelativesAbroadDAO {
}
