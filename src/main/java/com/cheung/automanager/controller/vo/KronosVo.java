package com.cheung.automanager.controller.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
//set返回当前对象的注解
public class KronosVo {
    private boolean sync;
    private String dataFileName;
    private Integer lockBackNum;
    private Integer predLenNum;
    private Integer startIndex;
    private boolean showResult;
    private boolean printAllResult;
    private boolean saveResult;
}
