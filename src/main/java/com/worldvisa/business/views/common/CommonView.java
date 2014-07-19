/******************************************************************************
 * Name : Cache.java Author : Administrator Date : 11-May-2010,8:57:13 PM Description : Implementation for Cache
 *****************************************************************************/
package com.worldvisa.business.views.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.activation.MimetypesFileTypeMap;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.io.FileTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import com.worldvisa.business.domain.common.FileBean;
import com.worldvisa.business.domain.common.Lookup;
import com.worldvisa.business.domain.common.MenuItem;
import com.worldvisa.business.domain.profile.EmployeeDetails;
import com.worldvisa.business.domain.profile.LoginDetails;
import com.worldvisa.business.infra.annotations.AuthenticationBypass;
import com.worldvisa.business.infra.base.BaseView;
import com.worldvisa.business.infra.constants.LookupConstants;
import com.worldvisa.business.service.stub.CommonService;
import com.worldvisa.business.service.stub.FileHandlerService;
import com.worldvisa.business.service.stub.ProfileService;

/**
 * 
 */
@RemoteProxy
public class CommonView
extends BaseView {
    private static final long  serialVersionUID = -5637540027161169095L;
    @Autowired
    private CommonService      commonService;
    @Autowired
    private FileHandlerService fileHandlerService;
    @Autowired
    private ProfileService     profileService;

    @RemoteMethod
    public List<EmployeeDetails> getAllEmployees()
    throws Exception {
        return this.profileService.readAllEmployee();
    }

    @RemoteMethod
    @AuthenticationBypass
    public Map<Long, Lookup> getAllLookupMap()
    throws Exception {
        return this.commonService.readLookupMap();
    }

    @RemoteMethod
    public List<EmployeeDetails> getAssignmentOptions()
    throws Exception {
        return this.profileService.readAssignmentOptions(this.user().getEmailId());
    }

    @RemoteMethod
    @AuthenticationBypass
    public List<String> getCountriesList(final String partialName)
    throws Exception {
        final List<Lookup> countryList = this.commonService.readLookupList(118L);
        final List<String> countries = new ArrayList<String>();
        final String uCase = (partialName == null ? "" : partialName.toUpperCase());
        for (final Lookup country : countryList) {
            if (country.getLookupDescription().toUpperCase().startsWith(uCase)) {
                countries.add(country.getLookupDescription());
            }
        }
        return countries;
    }

    @RemoteMethod
    @AuthenticationBypass
    public FileTransfer getFile(final FileBean fileObject)
    throws Exception {
        final File file = this.fileHandlerService.readFile(fileObject);
        try {
            return new FileTransfer(fileObject.getFileName(), new MimetypesFileTypeMap().getContentType(file), new FileInputStream(file));
        } catch (final FileNotFoundException e) {
            throw new Exception(
            "The file you are looking for is not available in the system. If the problem persists, Please contact the administrator.");
        }
    }

    /**
     * @return the lookup
     * @throws Exception
     */
    @RemoteMethod
    @AuthenticationBypass
    public Map<Long, Lookup> getLookupMap(final Long parentLookupId)
    throws Exception {
        final List<Lookup> returnValues = this.commonService.readLookupList(parentLookupId);
        final Map<Long, Lookup> lookupMap = new HashMap<Long, Lookup>();
        for (final Lookup val : returnValues) {
            lookupMap.put(val.getLookupId(), val);
        }
        return lookupMap;
    }

    @RemoteMethod
    @AuthenticationBypass
    public Map<String, Object> getMenu() {
        final LoginDetails loggedInUser = this.user();
        if (loggedInUser == null) {
            return null;
        }
        final List<MenuItem> menuList = new ArrayList<MenuItem>();
        List<MenuItem> subMenuList;
        if (!loggedInUser.getUserRole().equals(LookupConstants.USER_ROLE.Customer)) {
            menuList.add(new MenuItem(1L, "workflow/Notification", "Tasks", "Check Acknowledge and Postpone notifications."));
            subMenuList = new ArrayList<MenuItem>();
            subMenuList.add(new MenuItem(201L, "request/UnassignedRequest", "Common Pool", "Common Pool"));
            subMenuList.add(new MenuItem(202L, "request/MyAssignments", "Active Assignments", "Active Assignment"));
            subMenuList.add(new MenuItem(203L, "request/ReportDetails", "Fill a Form", "Fill a Form"));
            menuList.add(new MenuItem(2L, "request/MyAssignments", "Data Management", "Data Management", subMenuList));
            subMenuList = new ArrayList<MenuItem>();
            subMenuList.add(new MenuItem(301L, "workflow/DailyReporting", "Daily Report", "Daily Reporting"));
            subMenuList.add(new MenuItem(302L, "profile/EmployeeManagement", "My Collegues", "My Collegues"));
            if (loggedInUser.getUserRole().equals(LookupConstants.USER_ROLE.Manager)) {
                subMenuList.add(new MenuItem(303L, "profile/EmployeeDetails", "Add new Employee", "Add a new Employee"));
            }
            menuList.add(new MenuItem(3L, "workflow/DailyReporting", "My Space", "My Space", subMenuList));
            System.out.println(loggedInUser.getUserRole());
            if (loggedInUser.getUserRole().equals(LookupConstants.USER_ROLE.Manager)) {
                menuList.add(new MenuItem(4L, "workflow/TransactionRecords", "Transaction Records", "Transaction Records"));
            }
            subMenuList = new ArrayList<MenuItem>();
            subMenuList.add(new MenuItem(502L, "market/ListGroups", "List Groups", "List Groups"));
            subMenuList.add(new MenuItem(503L, "market/AddContacts", "Add Contacts", "Add Contacts"));
            subMenuList.add(new MenuItem(504L, "market/ListTemplates", "List Templates", "List Templates"));
            subMenuList.add(new MenuItem(505L, "market/AddTemplate", "Add Template", "Add Template"));
            subMenuList.add(new MenuItem(502L, "market/ListContacts", "List Contacts", "List Contacts"));
            menuList.add(new MenuItem(5L, "market/ListContacts", "Market", "Data Management", subMenuList));
        }
        final Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("user", loggedInUser);
        returnMap.put("menuList", menuList);
        return returnMap;
    }
}
