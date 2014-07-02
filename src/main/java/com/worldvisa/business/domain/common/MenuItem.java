package com.worldvisa.business.domain.common;

import java.util.List;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.BeanConverter;
import com.worldvisa.business.infra.base.BaseBean;

@DataTransferObject(converter = BeanConverter.class)
public class MenuItem
extends BaseBean {
    private static final long serialVersionUID = -9115320631556918817L;
    private List<MenuItem>    childMenu;
    private String            description;
    private String            displayName;
    private String            href;
    private Long              id;

    public MenuItem() {
        // Default Constructor
    }

    public MenuItem(final Long id, final String href, final String displayName, final String description) {
        super();
        this.id = id;
        this.href = href;
        this.displayName = displayName;
        this.description = description;
    }

    public MenuItem(final Long id, final String href, final String displayName, final String description, final List<MenuItem> childMenu) {
        super();
        this.id = id;
        this.href = href;
        this.displayName = displayName;
        this.description = description;
        this.childMenu = childMenu;
    }

    /**
     * @return the childMenu
     */
    public List<MenuItem> getChildMenu() {
        return this.childMenu;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @return the displayName
     */
    public String getDisplayName() {
        return this.displayName;
    }

    /**
     * @return the href
     */
    public String getHref() {
        return this.href;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return this.id;
    }

    /**
     * @param childMenu the childMenu to set
     */
    public void setChildMenu(final List<MenuItem> childMenu) {
        this.childMenu = childMenu;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * @param displayName the displayName to set
     */
    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }

    /**
     * @param href the href to set
     */
    public void setHref(final String href) {
        this.href = href;
    }

    /**
     * @param id the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }
}
