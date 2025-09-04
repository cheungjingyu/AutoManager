package com.cheung.automanager.client;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TushareParam {
    private String api_name;
    private String token;
    private Object params;
    private String fields;
}
