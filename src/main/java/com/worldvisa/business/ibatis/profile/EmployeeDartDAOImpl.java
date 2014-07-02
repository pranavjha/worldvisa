/******************************************************************************
 * Name : EmployeeDartDAOImpl.java Author : Administrator Date : 2010/05/26 Description : Implementation for the EmployeeDartDAOImpl class
 *****************************************************************************/
package com.worldvisa.business.ibatis.profile;

import org.springframework.stereotype.Repository;
import com.worldvisa.business.dao.profile.EmployeeDartDAO;
import com.worldvisa.business.domain.profile.EmployeeDart;
import com.worldvisa.business.infra.base.BaseIbatisDAOImpl;

/**
 * iBatis DAO implementation for interfacing with the ADMIN.PROFILE_EMPLOYEE_DART table<br/>
 */
@Repository("profileEmployeeDartDAOImpl")
public class EmployeeDartDAOImpl
extends BaseIbatisDAOImpl<EmployeeDart>
implements EmployeeDartDAO {
}
