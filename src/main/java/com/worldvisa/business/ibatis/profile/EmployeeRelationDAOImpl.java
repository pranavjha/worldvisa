/******************************************************************************
 * Name : LoginDetailsDAOImpl.java Author : Administrator Date : 2010/05/08 Description : Implementation for the LoginDetailsDAOImpl class
 *****************************************************************************/
package com.worldvisa.business.ibatis.profile;

import org.springframework.stereotype.Repository;
import com.worldvisa.business.dao.profile.EmployeeRelationDAO;
import com.worldvisa.business.domain.profile.EmployeeRelation;
import com.worldvisa.business.infra.base.BaseIbatisDAOImpl;

/**
 * iBatis DAO implementation for interfacing with the ADMIN.PROFILE_LOGIN_DETAILS table<br/>
 */
@Repository("profileEmployeeRelationDAOImpl")
public class EmployeeRelationDAOImpl
extends BaseIbatisDAOImpl<EmployeeRelation>
implements EmployeeRelationDAO {
}
