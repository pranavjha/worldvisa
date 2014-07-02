package com.worldvisa.business.views.workflow;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import com.worldvisa.business.domain.workflow.SampleFilter;
import com.worldvisa.business.infra.annotations.AuthenticationBypass;
import com.worldvisa.business.infra.base.BaseView;

@RemoteProxy
public class SampleView
extends BaseView {
    private static final long serialVersionUID = 6765410879971550999L;
    private final String      filePath         = "/home/BackendSampler.xls";

    private List<List<String>> readAll(SampleFilter filter)
    throws Exception {
        final File worksheetFile = new File(this.filePath);
        Workbook workbook = null;
        final List<List<String>> extractedList = new ArrayList<List<String>>();
        if (filter.getApsNumber() == null) {
            filter.setApsNumber("");
        }
        if (filter.getPo() == null) {
            filter.setPo("");
        }
        if (filter.getDestination() == null) {
            filter.setDestination("");
        }
        if (filter.getStatus() == null) {
            filter.setStatus("");
        }
        try {
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
                if (row.get(0).contains(filter.getApsNumber()) && row.get(4).contains(filter.getPo()) && row.get(7).contains(filter.getDestination())
                && row.get(19).contains(filter.getStatus())) {
                    extractedList.add(row);
                }
            }
        } finally {
            if (null != workbook) {
                workbook.close();
            }
        }
        return extractedList;
    }

    @RemoteMethod
    @AuthenticationBypass
    public List<List<String>> readList(SampleFilter filter)
    throws Exception {
        final List<List<String>> totalList = this.readAll(filter);
        if (filter.getStartRow() >= totalList.size()) {
            filter.setStartRow(0);
        }
        return totalList.subList(filter.getStartRow(), Math.min(filter.getEndRow(), totalList.size()));
    }

    @RemoteMethod
    @AuthenticationBypass
    public Integer readListLength(SampleFilter filter)
    throws Exception {
        return this.readAll(filter).size();
    }
}
