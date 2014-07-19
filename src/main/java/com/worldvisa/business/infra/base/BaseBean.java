/******************************************************************************
 * Name : BaseBean.java Author : pranav_jha Date : 2010/02/22 Description : Base class for all domain objects in the application
 *****************************************************************************/
package com.worldvisa.business.infra.base;

import java.io.Serializable;

/**
 * Base class for all domain objects in the application
 */
public abstract class BaseBean
implements Serializable {
    private static final long serialVersionUID = -3696740529578132473L;

    public String getType() {
        return this.getClass().getName();
    }
}
