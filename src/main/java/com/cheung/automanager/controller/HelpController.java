package com.cheung.automanager.controller;

import com.cheung.automanager.service.HelpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "help")
@RestController("/help")
@RequiredArgsConstructor
public class HelpController {

    private final HelpService helpService;

    @ApiOperation(value = "将中文文案同步到Eevee {容器内执行passwd 123456}")
    @GetMapping("/syncApplication")
    public ResponseEntity<String> syncApplication() {
        return ResponseEntity.ok(helpService.help());
    }
}
