package com.cheung.automanager.controller;

import com.cheung.automanager.service.DataDownloadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "history")
@RestController("/history/day")
@RequiredArgsConstructor
public class HistoryDayController {

    private final DataDownloadService dataDownloadService;

    @ApiOperation(value = "获取日历史数据")
    @GetMapping("/download")
    public ResponseEntity<?> load(String tsCode) throws Exception {
        dataDownloadService.downloadDayData(tsCode);
        return ResponseEntity.ok("success");
    }
}
