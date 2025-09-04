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

        /**
     * 执行策略接口
     *
     * @param sync 是否同步执行，默认为true
     * @param dataFileName 数据文件名，默认为"XSHG_5min_600977.csv"
     * @param startIndex 起始索引，默认为0
     * @param lockBackNum 回溯数量，默认为200
     * @param predLenNum 预测长度数量，默认为40
     * @param showResult 是否渲染结果，默认为true
     * @param printAllResult 是否打印所有结果，默认为false
     * @return ResponseEntity<?> 策略执行结果的响应实体
     * @throws Exception 执行过程中可能抛出的异常
     */
    @GetMapping("/exec")
    @ApiOperation(value = "执行策略", position = 1)
    public ResponseEntity<?> exec(@RequestParam(defaultValue = "true") boolean sync,
                                  @RequestParam(defaultValue = "XSHG_5min_600977.csv") String dataFileName,
                                  @RequestParam(defaultValue = "0") Integer startIndex,
                                  @RequestParam(defaultValue = "200") Integer lockBackNum,
                                  @RequestParam(defaultValue = "40") Integer predLenNum,
                                  @RequestParam(defaultValue = "true") boolean showResult,
                                  @RequestParam(defaultValue = "false") boolean printAllResult) throws Exception {
        // 构建策略执行参数对象
        KronosVo kronosVo = new KronosVo()
                .setSync(sync)
                .setDataFileName(dataFileName)
                .setStartIndex(startIndex)
                .setLockBackNum(lockBackNum)
                .setPredLenNum(predLenNum)
                .setShowResult(showResult)
                .setPrintAllResult(printAllResult);
        return ResponseEntity.ok(strategyService.exec(kronosVo));
    }

}
