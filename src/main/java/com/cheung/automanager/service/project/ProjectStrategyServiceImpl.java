package com.cheung.automanager.service.project;

import com.cheung.automanager.service.StrategyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

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
    public Object exec(String shell, boolean sync) {
        // 执行指令: python  /Users/cheung/Downloads/Kronos-master/examples/prediction_example_cpu.py
        try {
            // 定义工作目录
            File workingDir = new File("/Users/cheung/Downloads/Kronos-master/examples");

            // 使用 ProcessBuilder 执行命令
            ProcessBuilder pb = new ProcessBuilder("/usr/bin/python3", "prediction_example_cpu.py");
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


}
