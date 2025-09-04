package com.cheung.automanager.service.project;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.text.csv.CsvWriter;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cheung.automanager.client.DailyParam;
import com.cheung.automanager.client.TushareClient;
import com.cheung.automanager.client.TushareParam;
import com.cheung.automanager.config.AutoConstant;
import com.cheung.automanager.service.DataDownloadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cheung.automanager.config.AutoConstant.DATA_CSV_PATH;
import static com.cheung.automanager.config.AutoConstant.KRONOS_EXAMPLE_PATH;
import static com.cheung.automanager.config.AutoConstant.TUSHARE_API_URL;
import static com.cheung.automanager.config.AutoConstant.TUSHARE_TOKEN;

@Slf4j
@Service
public class DataDownloadServiceImpl implements DataDownloadService {


    /**
     * 获取月份数据接口
     * 根据Tushare文档，此接口用于获取交易日历等月份相关数据
     *
     * @param exchange  交易所代码 (SSE: 上交所, SZSE: 深交所)
     * @param startDate 开始日期 (格式: yyyyMMdd)
     * @param endDate   结束日期 (格式: yyyyMMdd)
     * @return 接口返回的JSON数据
     */
    public String getMonthlyData(String exchange, String startDate, String endDate) {
        try {
            // 构建请求参数
            Map<String, Object> params = new HashMap<>();
            params.put("api_name", "daily");
            params.put("token", TUSHARE_TOKEN);
            params.put("fields", "trade_date,open,high,low,close,volume,amount");

            // 构建请求数据
            JSONObject requestData = new JSONObject();
            requestData.put("params", exchange);

            if (StrUtil.isNotBlank(startDate)) {
                requestData.put("start_date", startDate);
            }

            if (StrUtil.isNotBlank(endDate)) {
                requestData.put("end_date", endDate);
            }
            if (StrUtil.isNotBlank(endDate)) {
                requestData.put("end_date", endDate);
            }

            params.put("params", requestData);

            // 发送POST请求
            HttpResponse response = HttpRequest.post(TUSHARE_API_URL)
                    .header("Content-Type", "application/json")
                    .body(JSONUtil.toJsonStr(params))
                    .execute();

            if (response.getStatus() == 200) {
                return response.body();
            } else {
                log.error("Tushare API request failed with status: {}", response.getStatus());
                return null;
            }
        } catch (Exception e) {
            log.error("Error occurred while fetching monthly data from Tushare", e);
            return null;
        }
    }

    /**
     * 获取指定年月的数据
     *
     * @param exchange 交易所代码
     * @param year     年份
     * @param month    月份
     * @return 该月份的交易日历数据
     */
    public String getMonthlyData(String exchange, int year, int month) {
        // 构造开始和结束日期
        String startDate = String.format("%d%02d01", year, month);

        // 计算该月最后一天
        String endDate;
        if (month == 12) {
            endDate = String.format("%d1231", year);
        } else {
            // 使用下个月第一天减1天得到当前月最后一天
            Date firstDayNextMonth = DateUtil.parse(String.format("%d%02d01", year, month + 1), "yyyyMMdd");
            Date lastDay = DateUtil.offsetDay(firstDayNextMonth, -1);
            endDate = DateUtil.format(lastDay, "yyyyMMdd");
        }

        return getMonthlyData(exchange, startDate, endDate);
    }

    /**
     * 获取当前月份数据
     *
     * @param exchange 交易所代码
     * @return 当前月份的交易日历数据
     */
    public String getCurrentMonthlyData(String exchange) {
        Date now = new Date();
        int year = DateUtil.year(now);
        int month = DateUtil.month(now) + 1; // DateUtil.month返回0-11，需要+1

        return getMonthlyData(exchange, year, month);
    }

    @Override
    public Object downloadDayData(String tsCode) {
        //请求结果
        DailyParam dailyParam = new DailyParam()
                .setTs_code(tsCode);
        TushareParam tushareParam = new TushareParam()
                .setApi_name("daily")
                .setToken(TUSHARE_TOKEN)
                .setParams(dailyParam)
                .setFields("trade_date,open,high,low,close,vol,amount");
        String data = TushareClient.getData(tushareParam);
        //解析结果
        List<List<String>> rows = new ArrayList<>();
        JSONObject jsonObject = JSONUtil.parseObj(data);
        JSONObject dataObject = jsonObject.getJSONObject("data");
        List<String> fields = dataObject.getJSONArray("fields").toList(String.class);
        fields.remove("vol");
        fields.add(5,"volume");
        rows.add(fields);
        JSONArray jsonArray = dataObject.getJSONArray("items");
        for (int i = jsonArray.size() - 1; i >= 0; i--) {
            List<String> list = jsonArray.getJSONArray(i).toList(String.class);
            String date = list.get(0);
            date = date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8) + " 00:00:00";
            //date的格式为20250829 ，我现在要转成2024-06-18 13:30:00的格式
            list.set(0, date);
            rows.add(list);
        }
        //保存结果
        CsvWriter write = CsvUtil.getWriter(AutoConstant.getResourcePath(KRONOS_EXAMPLE_PATH+DATA_CSV_PATH), StandardCharsets.UTF_8);
        write.write(rows);
        write.close();
        return "data.csv";
    }
}
