package com.cheung.automanager.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

public interface HelpService {

    default String help() {
        //说明一下包含哪些模块和命令
        return "help";
    }
}
