package com.yupi.yupao.once;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 星球表格信息
 */
@Data
@EqualsAndHashCode
public class XingQiuUserInfo {
    /**
     * id
     */
    @ExcelProperty("成员编号")
    private String planet;

    /**
     * 成员昵称
     */
    @ExcelProperty("成员昵称")
    private String username;
}