package com.cheung.automanager.client;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@TushareAPI("daily")
@Accessors(chain = true)
public class DailyParam extends TushareParam {
    private String ts_code;
    private String trade_date;
    private String start_date;
    private String end_date;
    private String limit;
    private String offset;
}
