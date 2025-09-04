package com.cheung.automanager.service.project;

import cn.hutool.core.io.FileUtil;
import com.cheung.automanager.config.AutoConstant;
import com.cheung.automanager.controller.vo.KronosVo;
import com.cheung.automanager.service.StrategyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import static com.cheung.automanager.config.AutoConstant.DATA_FILE_NAME;
import static com.cheung.automanager.config.AutoConstant.EXEC_FILE_NAME;
import static com.cheung.automanager.config.AutoConstant.EXEC_FILE_NAME_MODEL;
import static com.cheung.automanager.config.AutoConstant.KRONOS_EXAMPLE_PATH;
import static com.cheung.automanager.config.AutoConstant.LOOK_BACK_NUMBER;
import static com.cheung.automanager.config.AutoConstant.PRED_LEN_NUMBER;
import static com.cheung.automanager.config.AutoConstant.PYTHON_PATH;

@Slf4j
@Service
public class ProjectStrategyServiceImpl implements StrategyService {

    public static final String STRATEGY_PATH = "strategy.properties";

    @Override
    public Object load() throws Exception {
        return ProjectFileCommonUtil.getProperties(ProjectFileCommonUtil.DATA_PATH + STRATEGY_PATH);
    }

    @Override
    public void save() {

    }

    @Override
    public void delete() {

    }

    @Override
    public void get(String name) {

    }

    @Override
    public Object exec(KronosVo kronosVo) {
        //替换数据文件名
        changeDataFileName(kronosVo);
        return execModel(kronosVo.isSync());
    }

    private Object execModel(boolean sync) {
        try {
            //获取当前项目下的resources完整路径
            String resourcePath = AutoConstant.getResourcePath(KRONOS_EXAMPLE_PATH);
            // 定义工作目录
            File workingDir = new File(resourcePath);

            // 使用 ProcessBuilder 执行命令
            ProcessBuilder pb = new ProcessBuilder(PYTHON_PATH, EXEC_FILE_NAME);
            pb.directory(workingDir); // 设置工作目录

            // 启动进程
            Process process = pb.start();

            if (sync) {
                // 等待执行完成
                int exitCode = process.waitFor();

                // 读取标准输出
                StringBuilder output = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        output.append(line).append("\n");
                    }
                }

                // 读取错误输出
                StringBuilder errorOutput = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getErrorStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        errorOutput.append(line).append("\n");
                    }
                }

                // 记录日志
                log.info("Python script executed with exit code: {}", exitCode);
                log.info("Python script output: {}", output.toString());
                if (errorOutput.length() > 0) {
                    log.error("Python script error output: {}", errorOutput.toString());
                }

                // 返回执行结果
                if (exitCode == 0) {
                    return output.toString();
                } else {
                    return "Execution failed with exit code: " + exitCode + ", Error: " + errorOutput.toString();
                }
            } else {
                return "success";
            }
        } catch (Exception e) {
            log.error("Failed to execute python script", e);
            return "Execution failed: " + e.getMessage();
        }
    }

    private void changeDataFileName(KronosVo kronosVo) {
        // 读取模型文件内容
        String resourcesPath = KRONOS_EXAMPLE_PATH + EXEC_FILE_NAME_MODEL;
        String modelPath = AutoConstant.getResourcePath(resourcesPath);
        String context = FileUtil.readString(modelPath, Charset.defaultCharset());

        // 替换数据文件名称
        context = context.replaceAll(DATA_FILE_NAME, kronosVo.getDataFileName());
        context = context.replaceAll(LOOK_BACK_NUMBER, kronosVo.getLockBackNum().toString());
        context = context.replaceAll(PRED_LEN_NUMBER, kronosVo.getPredLenNum().toString());

        // 将修改后的内容写回模型文件
        String writePath = AutoConstant.getResourcePath(KRONOS_EXAMPLE_PATH + EXEC_FILE_NAME);
        FileUtil.writeString(context, writePath, Charset.defaultCharset());
    }
}
