package com.cheung.automanager.config;

import java.util.Objects;

public class AutoConstant {
    /**
     * 配置文件分隔符
     */
    public static final String prop_equal_split = "=";
    /**
     * 配置文件换行符
     */
    public static final String prop_enter_split = "/n";
    /**
     * 自定义文件名
     */
    public static final String DATA_FILE_NAME = "DATA_FILE_NAME";

    public static final String EXEC_FILE_NAME = "prediction_example_cpu.py";
    public static final String EXEC_FILE_NAME_MODEL = "prediction_example_cpu_model.py";

    public static final String PYTHON_PATH = "/usr/bin/python3";

    public static final String KRONOS_EXAMPLE_PATH = "Kronos-master/examples/";
    public static final String LOOK_BACK_NUMBER = "LOOK_BACK_NUMBER";
    public static final String START_INDEX = "START_INDEX";
    public static final String PRED_LEN_NUMBER = "PRED_LEN_NUMBER";
    public static final String SHOW_RESULT = "SHOW_RESULT";
    public static final String PRINT_ALL_RESULT = "PRINT_ALL_RESULT";

    public static String getResourcePath(String path) {
        return Objects.requireNonNull(AutoConstant.class.getClassLoader().getResource(path)).getPath();
    }
}
