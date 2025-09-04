package com.cheung.automanager.controller.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
//set返回当前对象的注解
public class KronosVo {
    boolean sync;
    String dataFileName;
    Integer lockBackNum;
    Integer predLenNum;
    Integer startIndex;
    boolean showResult;
    boolean printAllResult;
}
