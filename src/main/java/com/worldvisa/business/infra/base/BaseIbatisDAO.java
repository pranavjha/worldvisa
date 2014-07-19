/******************************************************************************
 * Name : BaseIbatisDAO.java Author : pranav_jha Date : 2010/02/23 Description : Base interface for all iBATIS DAO interfaces
 *****************************************************************************/
package com.worldvisa.business.infra.base;

import java.sql.SQLException;
import java.util.List;

/**
 * Base interface for all iBATIS DAO interfaces
 */
public interface BaseIbatisDAO<T> {
    /**
     * Inserts a row mapped by the bean T into the database
     * @param t Backing bean containing the data from which a row is to be created
     * @throws Exception
     */
    public Object create(T t)
    throws Exception;

    /**
     * Deletes rows filtered by the criteria based on the parameter object
     * @param t The object defining the filter criteria
     * @throws Exception
     */
    public int delete(T t)
    throws Exception;

    /**
     * Reads a row filtered by the criteria based on the parameter object
     * @param t The object defining the filter criteria
     * @return A row from the database corresponding satisfying the filter criteria
     * @throws Exception
     */
    public T read(T t)
    throws Exception;

    /**
     * Reads rows filtered by the criteria based on the parameter object
     * @param t The object defining the filter criteria
     * @return Rows from the database corresponding satisfying the filter criteria
     */
    public List<T> readList(T t)
    throws Exception;

    /**
     * Updates rows filtered by the criteria based on the parameter object
     * @param data The object used to update the record
     * @param filter The object defining the filter criteria
     * @return Number of rows updated
     * @throws Exception
     */
    public int update(T data, T filter)
    throws SQLException;
}
