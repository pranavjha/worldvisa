/******************************************************************************
 * Name : FamilyDetailsDAOImpl.java Author : Administrator Date : 2010/05/08 Description : Implementation for the FamilyDetailsDAOImpl class
 *****************************************************************************/
package com.worldvisa.business.ibatis.request;

import org.springframework.stereotype.Repository;
import com.worldvisa.business.dao.request.FamilyDetailsDAO;
import com.worldvisa.business.domain.request.FamilyDetails;
import com.worldvisa.business.infra.base.BaseIbatisDAOImpl;

/**
 * iBatis DAO implementation for interfacing with the ADMIN.REQUEST_FAMILY_DETAILS table<br/>
 */
@Repository("requestFamilyDetailsDAOImpl")
public class FamilyDetailsDAOImpl
extends BaseIbatisDAOImpl<FamilyDetails>
implements FamilyDetailsDAO {
}
