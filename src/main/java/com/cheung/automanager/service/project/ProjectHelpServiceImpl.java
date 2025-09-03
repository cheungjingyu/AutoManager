package com.cheung.automanager.service.project;

import com.cheung.automanager.service.HelpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class ProjectHelpServiceImpl implements HelpService {

    public static final String helpFilePath = "help/project/help.md";

    @Override
    public String help() {
        try {
            ClassPathResource resource = new ClassPathResource(helpFilePath);
            return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            return HelpService.super.help();
        }
    }
}
