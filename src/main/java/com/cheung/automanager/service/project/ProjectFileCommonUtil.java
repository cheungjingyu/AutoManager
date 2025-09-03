package com.cheung.automanager.service.project;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Slf4j
public class ProjectFileCommonUtil {
    public static final String DATA_PATH = "data/";

    public static String getData(String path) throws Exception {
        ClassPathResource resource = new ClassPathResource(path);
        return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
    }

    public static Properties getProperties(String path) throws Exception {
        ClassPathResource resource = new ClassPathResource(path);
        Properties properties = new Properties();
        // 使用 InputStreamReader 指定 UTF-8 编码来解决中文乱码问题
        properties.load(new java.io.InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
        return properties;
    }

}
