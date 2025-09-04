package com.cheung.automanager.controller;

import com.cheung.automanager.controller.vo.KronosVo;
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

    @GetMapping("/exec")
    @ApiOperation(value = "执行策略", position = 1)
    public ResponseEntity<?> exec(@RequestParam(defaultValue = "true") boolean sync,
                                  @RequestParam(defaultValue = "XSHG_5min_600977.csv") String dataFileName,
                                  @RequestParam(defaultValue = "200") Integer lockBackNum,
                                  @RequestParam(defaultValue = "40") Integer predLenNum) throws Exception {
        KronosVo kronosVo = new KronosVo()
                .setSync(sync)
                .setDataFileName(dataFileName)
                .setLockBackNum(lockBackNum)
                .setPredLenNum(predLenNum);
        return ResponseEntity.ok(strategyService.exec(kronosVo));
    }
}
