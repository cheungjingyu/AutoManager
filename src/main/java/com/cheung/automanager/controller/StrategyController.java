package com.cheung.automanager.controller;

import com.cheung.automanager.controller.vo.KronosVo;
import com.cheung.automanager.service.StrategyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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

    @GetMapping("/exec")
    @ApiOperation(value = "执行策略", position = 1)
    @ApiImplicitParam(name = "projectName", value = "项目名", required = true, defaultValue = "ta-common-service20240506")
    public ResponseEntity<?> exec(@RequestParam(defaultValue = "true") boolean sync,
                                  @RequestParam(defaultValue = "XSHG_5min_600977.csv") String dataFileName,
                                  Integer lockBackNum,
                                  Integer predLenNum) throws Exception {
        KronosVo kronosVo = new KronosVo()
                .setSync(sync)
                .setDataFileName(dataFileName)
                .setLockBackNum(lockBackNum)
                .setPredLenNum(predLenNum);
        return ResponseEntity.ok(strategyService.exec(kronosVo));
    }
}
