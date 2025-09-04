package com.cheung.automanager.client;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

import static com.cheung.automanager.config.AutoConstant.TUSHARE_API_URL;

@Slf4j
public class TushareClient {

    public static String getData(TushareParam tushareParam) {
        HttpResponse response = HttpRequest.post(TUSHARE_API_URL)
                .header("Content-Type", "application/json")
                .body(JSONUtil.toJsonStr(tushareParam))
                .execute();
        if (response.getStatus() == 200) {
            return response.body();
        } else {
            log.error("Tushare API request failed with status: {}", response.getStatus());
            throw new RuntimeException("Tushare API request failed");
        }
    }
}
