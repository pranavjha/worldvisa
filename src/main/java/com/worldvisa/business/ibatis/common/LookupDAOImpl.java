/******************************************************************************
 * Name : LookupDAOImpl.java Author : Administrator Date : 2010/05/08 Description : Implementation for the LookupDAOImpl class
 *****************************************************************************/
package com.worldvisa.business.ibatis.common;

import org.springframework.stereotype.Repository;
import com.worldvisa.business.dao.common.LookupDAO;
import com.worldvisa.business.domain.common.Lookup;
import com.worldvisa.business.infra.base.BaseIbatisDAOImpl;

/**
 * iBatis DAO implementation for interfacing with the ADMIN.COMMON_LOOKUP table<br/>
 */
@Repository("commonLookupDAOImpl")
public class LookupDAOImpl
extends BaseIbatisDAOImpl<Lookup>
implements LookupDAO {
}
