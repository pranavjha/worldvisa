/******************************************************************************
 * Name : LangProfeciencyDAOImpl.java Author : Administrator Date : 2010/05/08 Description : Implementation for the LangProfeciencyDAOImpl class
 *****************************************************************************/
package com.worldvisa.business.ibatis.request;

import org.springframework.stereotype.Repository;
import com.worldvisa.business.dao.request.LangProfeciencyDAO;
import com.worldvisa.business.domain.request.LangProfeciency;
import com.worldvisa.business.infra.base.BaseIbatisDAOImpl;

/**
 * iBatis DAO implementation for interfacing with the ADMIN.REQUEST_LANG_PROFECIENCY table<br/>
 */
@Repository("requestLangProfeciencyDAOImpl")
public class LangProfeciencyDAOImpl
extends BaseIbatisDAOImpl<LangProfeciency>
implements LangProfeciencyDAO {
}
