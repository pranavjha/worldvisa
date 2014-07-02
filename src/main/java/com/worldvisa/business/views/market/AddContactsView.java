/**
 * 
 */
package com.worldvisa.business.views.market;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import com.worldvisa.business.domain.common.DWRResponse;
import com.worldvisa.business.domain.common.WebMessage;
import com.worldvisa.business.domain.market.User;
import com.worldvisa.business.domain.market.UserGroup;
import com.worldvisa.business.domain.market.UserGroupFilter;
import com.worldvisa.business.domain.market.UserLoader;
import com.worldvisa.business.infra.base.BaseView;
import com.worldvisa.business.infra.constants.LookupConstants;
import com.worldvisa.business.infra.constants.MessageSeverity;
import com.worldvisa.business.infra.utils.BasicUtils;
import com.worldvisa.business.service.stub.FileHandlerService;
import com.worldvisa.business.service.stub.MarketService;
import com.worldvisa.business.service.stub.ProfileService;

/**
 * @author Administrator
 */
@RemoteProxy
public class AddContactsView
extends BaseView {
    private static final long  serialVersionUID = 5882116114765277848L;
    @Autowired
    private FileHandlerService fileHandlerService;
    @Autowired
    private MarketService      marketService;
    @Autowired
    private ProfileService     profileService;

    @RemoteMethod
    public List<String> autoCompleteDataGroup(final String partialName) {
        final List<String> list = new ArrayList<String>();
        try {
            final UserGroupFilter filter = new UserGroupFilter();
            filter.setupDefaults();
            filter.setUserGroupName(partialName);
            final List<UserGroup> userGroupList = this.marketService.readUserGroupList(filter);
            for (final UserGroup userGroup : userGroupList) {
                list.add(userGroup.getUserGroupName());
            }
        } catch (final Exception e) {
            // do nothing here
        }
        return list;
    }

    private User createUser(final List<String> attributeNames, final List<String> attributeValues, final UserGroup userGroup)
    throws Exception {
        String variableName, value;
        final User user = new User();
        for (int i = 0; i < attributeNames.size() && i < attributeValues.size(); i++) {
            value = attributeValues.get(i);
            variableName = attributeNames.get(i).trim();
            if (value == null || value.trim().length() == 0) {
                continue;
            }
            if ("name".equals(variableName)) {
                if (value.length() > 200) {
                    value = value.substring(0, 200);
                }
                user.setName(value);
            } else if ("gender".equals(variableName)) {
                if (value.length() > 6) {
                    value = value.substring(0, 6);
                }
                user.setGender(value);
            } else if ("age".equals(variableName)) {
                user.setAge(Long.parseLong(value));
            } else if ("qualCompleted".equals(variableName)) {
                if (value.length() > 200) {
                    value = value.substring(0, 200);
                }
                user.setQualCompleted(value);
            } else if ("email".equals(variableName)) {
                user.setEmail(value);
            } else if ("mobile".equals(variableName)) {
                user.setMobile(value);
            } else if ("desCountries".equals(variableName)) {
                if (value.length() > 200) {
                    value = value.substring(0, 200);
                }
                user.setDesCountries(value);
            } else if ("desCourse".equals(variableName)) {
                if (value.length() > 200) {
                    value = value.substring(0, 200);
                }
                user.setDesCourse(value);
            } else if ("specialization".equals(variableName)) {
                if (value.length() > 200) {
                    value = value.substring(0, 200);
                }
                user.setSpecialization(value);
            } else if ("desCollege".equals(variableName)) {
                if (value.length() > 200) {
                    value = value.substring(0, 200);
                }
                user.setDesCollege(value);
            }
        }
        user.setUserGroupId(userGroup.getUserGroupId());
        user.setAddedBy(this.user().getEmailId());
        user.setAddedOn(BasicUtils.getCurrentDateTime());
        return user;
    }

    @RemoteMethod
    public void deleteContact(final UserLoader loader, final int rowIndex)
    throws Exception {
        final List<List<String>> allContacts = this.getContacts(loader);
        allContacts.remove(rowIndex);
        this.overwriteContacts(loader, allContacts);
    }

    @SuppressWarnings("unchecked")
    private List<List<String>> getContacts(final UserLoader loader)
    throws Exception {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(new File(loader.getListFilePath())));
            return (List<List<String>>) ois.readObject();
        } finally {
            if (null != ois) {
                ois.close();
            }
        }
    }

    /**
     * @see com.worldvisa.business.infra.base.BaseView#load()
     */
    @RemoteMethod
    public List<String[]> getTableColumnList()
    throws Exception {
        final List<String[]> tableColumnList = new ArrayList<String[]>();
        tableColumnList.add(new String[] {
                "-", "-none-"
        });
        tableColumnList.add(new String[] {
                "name", "Name"
        });
        tableColumnList.add(new String[] {
                "gender", "Gender"
        });
        tableColumnList.add(new String[] {
                "age", "Age"
        });
        tableColumnList.add(new String[] {
                "qualCompleted", "Qualification Completed"
        });
        tableColumnList.add(new String[] {
                "email", "e-mail"
        });
        tableColumnList.add(new String[] {
                "mobile", "Mobile"
        });
        tableColumnList.add(new String[] {
                "desCountries", "Desired Countries"
        });
        tableColumnList.add(new String[] {
                "desCourse", "Desired Course"
        });
        tableColumnList.add(new String[] {
                "specialization", "Specialization"
        });
        tableColumnList.add(new String[] {
                "desCollege", "Desired College"
        });
        tableColumnList.add(new String[] {
                "telephoneNo", "Landline"
        });
        tableColumnList.add(new String[] {
                "dateOfBirth", "Date of Birth"
        });
        return tableColumnList;
    }

    private void overwriteContacts(final UserLoader loader, final List<List<String>> contacts)
    throws Exception {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(new File(loader.getListFilePath())));
            oos.writeObject(contacts);
        } finally {
            if (null != oos) {
                oos.flush();
                oos.close();
            }
        }
    }

    @RemoteMethod
    public List<List<String>> readContacts(final UserLoader loader)
    throws Exception {
        final List<List<String>> allContacts = this.getContacts(loader);
        final List<List<String>> retVal = new ArrayList<List<String>>();
        for (int i = loader.getStartRow(); i < loader.getEndRow() && i < allContacts.size(); i++) {
            allContacts.get(i).add("" + i);
            retVal.add(allContacts.get(i));
        }
        return retVal;
    }

    @RemoteMethod
    public Integer readContactsColumns(final UserLoader loader)
    throws Exception {
        Workbook workbook = null;
        try {
            if (loader.getUploadedFile() == null) {
                return 0;
            }
            final File worksheetFile = this.fileHandlerService.readFile(loader.getUploadedFile());
            workbook = Workbook.getWorkbook(worksheetFile);
            return workbook.getSheet(0).getColumns();
        } finally {
            if (null != workbook) {
                workbook.close();
            }
        }
    }

    @RemoteMethod
    public Integer readContactsSize(final UserLoader loader)
    throws Exception {
        return this.getContacts(loader).size();
    }

    @RemoteMethod
    public UserLoader setupContacts(final UserLoader loader)
    throws Exception {
        Workbook workbook = null;
        try {
            final List<List<String>> extractedList = new ArrayList<List<String>>();
            final File worksheetFile = this.fileHandlerService.readFile(loader.getUploadedFile());
            workbook = Workbook.getWorkbook(worksheetFile);
            final Sheet sheet = workbook.getSheet(0);
            final int numRows = sheet.getRows();
            List<String> row;
            for (int i = 0; i < numRows; i++) {
                row = new ArrayList<String>();
                final Cell[] rows = sheet.getRow(i);
                for (final Cell row2 : rows) {
                    row.add(row2.getContents());
                }
                extractedList.add(row);
            }
            final File file = this.fileHandlerService.createTempFile();
            final ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(extractedList);
            oos.flush();
            oos.close();
            loader.setListFilePath(file.getAbsolutePath());
            return loader;
        } finally {
            if (null != workbook) {
                workbook.close();
            }
        }
    }

    @RemoteMethod
    public DWRResponse<Boolean> uploadContacts(final UserLoader loader, final List<String> columnOrder)
    throws Exception {
        final DWRResponse<Boolean> response = new DWRResponse<Boolean>();
        final List<List<String>> allContacts = this.getContacts(loader);
        final List<List<String>> nonUpdateableContacts = new ArrayList<List<String>>();
        final UserGroupFilter filter = new UserGroupFilter();
        filter.setupDefaults();
        final List<UserGroup> groupList = this.marketService.readUserGroupList(filter);
        UserGroup thisGroup = null;
        for (final UserGroup group : groupList) {
            if (group.getUserGroupName().equalsIgnoreCase(loader.getUserGroup().getUserGroupName())
            && group.getServiceType().equals(loader.getUserGroup().getServiceType())
            && group.getBranchOffice().equals(loader.getUserGroup().getBranchOffice())) {
                thisGroup = group;
                break;
            }
        }
        if (thisGroup == null) {
            thisGroup = this.marketService.createUserGroup(loader.getUserGroup(), this.user());
        }
        loader.setUserGroup(thisGroup);
        for (final List<String> oneContact : allContacts) {
            try {
                this.marketService.createNonRegUsers(this.createUser(columnOrder, oneContact, loader.getUserGroup()));
            } catch (final Exception e) {
                nonUpdateableContacts.add(oneContact);
            }
        }
        this.overwriteContacts(loader, nonUpdateableContacts);
        if (nonUpdateableContacts.size() == 0) {
            response.addMessage(new WebMessage(allContacts.size() + " contacts were successfully imported.", MessageSeverity.INFO));
        } else {
            response.addMessage(new WebMessage(nonUpdateableContacts.size() + " contacts could not be imported. Please review them. "
            + (allContacts.size() - nonUpdateableContacts.size()) + " contacts were successfully imported.", MessageSeverity.WARN));
        }
        this.profileService.createPublicDart(this.user(), "Uploaded " + allContacts.size() + " users for user group "
        + loader.getUserGroup().getUserGroupName(), LookupConstants.CASE_SUBSTATUS.Miscellaneous, null);
        return response;
    }
}
