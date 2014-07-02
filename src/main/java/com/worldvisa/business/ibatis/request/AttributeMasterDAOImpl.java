/******************************************************************************
 * Name : AttributeMasterDAOImpl.java Author : Administrator Date : 2010/05/25 Description : Implementation for the AttributeMasterDAOImpl class
 *****************************************************************************/
package com.worldvisa.business.ibatis.request;

import org.springframework.stereotype.Repository;
import com.worldvisa.business.dao.request.AttributeMasterDAO;
import com.worldvisa.business.domain.request.AttributeMaster;
import com.worldvisa.business.infra.base.BaseIbatisDAOImpl;

/**
 * iBatis DAO implementation for interfacing with the ADMIN.REQUEST_ATTRIBUTE_MASTER table<br/>
 */
@Repository("requestAttributeMasterDAOImpl")
public class AttributeMasterDAOImpl
extends BaseIbatisDAOImpl<AttributeMaster>
implements AttributeMasterDAO {
}
