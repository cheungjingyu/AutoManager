package com.cheung.automanager.config;

import java.util.Objects;

public class AutoConstant {
    /**
     * kronos策略相关配置
     */
    //文件位置
    public static final String KRONOS_EXAMPLE_PATH = "Kronos-master/examples/";
    //模板文件
    public static final String EXEC_FILE_NAME_MODEL = "prediction_example_cpu_model.py";
    //结果文件
    public static final String EXEC_FILE_NAME = "prediction_example_cpu.py";
    //python 路径
    public static final String PYTHON_PATH = "/usr/bin/python3";
    //数据文件名
    public static final String DATA_FILE_NAME = "DATA_FILE_NAME";
    //回看数量
    public static final String LOOK_BACK_NUMBER = "LOOK_BACK_NUMBER";
    //开始索引
    public static final String START_INDEX = "START_INDEX";
    //预测长度
    public static final String PRED_LEN_NUMBER = "PRED_LEN_NUMBER";
    //渲染结果
    public static final String SHOW_RESULT = "SHOW_RESULT";
    //打印所有结果
    public static final String PRINT_ALL_RESULT = "PRINT_ALL_RESULT";
    //保存结果
    public static final String SAVE_RESULT = "SAVE_RESULT";

    /**
     * tushare
     */
    //tushare token
    public static final String TUSHARE_TOKEN = "c3a6598ee4d859be3e3f1ae039fecf3d9f1505a421524e1eb72f5e53";
    // Tushare API URL
    public static final String TUSHARE_API_URL = "http://api.tushare.pro";
    //save data path
    public static final String DATA_CSV_PATH = "data/data.csv";

    /**
     * 获取资源文件的绝对路径
     *
     * @param path 资源文件的相对路径
     * @return 资源文件的绝对路径字符串
     * @throws NullPointerException 当指定路径的资源不存在时抛出
     */
    public static String getResourcePath(String path) {
        // 通过类加载器获取资源URL，然后获取其路径
        return Objects.requireNonNull(AutoConstant.class.getClassLoader().getResource(path)).getPath();
    }

}
