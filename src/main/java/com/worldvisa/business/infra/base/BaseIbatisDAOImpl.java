/******************************************************************************
 * Name : BaseIbatisDAOImpl.java Author : pranav_jha Date : 2010/02/21 Description : Base class for iBATIS DAO implementations
 *****************************************************************************/
package com.worldvisa.business.infra.base;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * Base class for iBATIS DAO implementations
 */
@Repository
public abstract class BaseIbatisDAOImpl<T>
extends SqlMapClientDaoSupport
implements BaseIbatisDAO<T> {
    protected static final Logger log = Logger.getLogger(BaseIbatisDAO.class);

    /**
     * @see com.bvonesource.foundation.base.BaseIbatisDAO#create(com.bvonesource.foundation.base.BaseBean)
     */
    public Object create(final T t)
    throws Exception {
        return this.getSqlMapClient().insert(this.getNameSpace(t) + ".create", t);
    }

    /**
     * @see com.bvonesource.foundation.base.BaseIbatisDAO#delete(com.bvonesource.foundation.base.BaseBean)
     */
    public int delete(final T t)
    throws Exception {
        return this.getSqlMapClient().delete(this.getNameSpace(t) + ".delete", t);
    }

    private String getNameSpace(final T t) {
        final String className = t.getClass().getName();
        final String[] packages = className.split("[.]");
        return packages[packages.length - 2] + packages[packages.length - 1];
    }

    /**
     * This method must be implemented by overriding classes to ensure that sqlMapClient gets autowired
     * @param sqlMapClient iBATIS Sql Map Client object
     */
    @Autowired
    public void init(final SqlMapClient sqlMapClient) {
        this.setSqlMapClient(sqlMapClient);
    }

    /**
     * @see com.bvonesource.foundation.base.BaseIbatisDAO#read(com.bvonesource.foundation.base.BaseBean)
     */
    @SuppressWarnings("unchecked")
    public T read(final T t)
    throws Exception {
        return (T) this.getSqlMapClient().queryForObject(this.getNameSpace(t) + ".read", t);
    }

    /**
     * @see com.bvonesource.foundation.base.BaseIbatisDAO#readList(com.bvonesource.foundation.base.BaseBean)
     */
    @SuppressWarnings("unchecked")
    public List<T> readList(final T t)
    throws Exception {
        final List<T> tempList = this.getSqlMapClient().queryForList(this.getNameSpace(t) + ".read", t);
        if (null == tempList) {
            return new ArrayList<T>();
        } else {
            return tempList;
        }
    }

    /**
     * @see com.bvonesource.foundation.base.BaseIbatisDAO#update(com.bvonesource.foundation.base.BaseBean, com.bvonesource.foundation.base.BaseBean)
     */
    public int update(final T data, final T filter)
    throws SQLException {
        final Map<String, Object> queryParam = new HashMap<String, Object>();
        queryParam.put("data", data);
        queryParam.put("filter", filter);
        return this.getSqlMapClient().update(this.getNameSpace(data) + ".update", queryParam);
    }
}
