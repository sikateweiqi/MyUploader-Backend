package cn.attackme.myuploader.model.excltemplate;

import cn.attackme.myuploader.utils.excel.ColorEnum;
import cn.attackme.myuploader.utils.excel.EnableExport;
import cn.attackme.myuploader.utils.excel.EnableExportField;
import cn.attackme.myuploader.utils.excel.ImportIndex;
import lombok.Data;

import java.io.Serializable;

@EnableExport(fileName = "")
@Data
public class SlrEmpSalary implements Serializable {
    @ImportIndex(index = 0)
    @EnableExportField(colName = "序号", colWidth = 80)
    private int seqNumber;
    @ImportIndex(index = 1)
    @EnableExportField(colName = "员工编号", colWidth = 160,cellColor = ColorEnum.RED)
    private String employeeCode;
    @ImportIndex(index = 2)
    @EnableExportField(colName = "员工姓名", colWidth = 160)
    private String employeeName;}
