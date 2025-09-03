package com.cheung.automanager.controller;

import com.cheung.automanager.service.StrategyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "strategy")
@RestController("/strategy")
@RequiredArgsConstructor
public class StrategyController {

    private final StrategyService strategyService;

    @ApiOperation(value = "查看所有策略")
    @GetMapping("/load")
    public ResponseEntity<?> load() throws Exception {
        return ResponseEntity.ok(strategyService.load());
    }

    @ApiOperation(value = "执行策略", position = 1)
    @GetMapping("/exec")
    public ResponseEntity<?> exec(String shell, @RequestParam(defaultValue = "false") boolean sync) throws Exception {
        return ResponseEntity.ok(strategyService.exec(shell, sync));
    }
}
