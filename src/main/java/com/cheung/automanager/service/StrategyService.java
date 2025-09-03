package com.cheung.automanager.service;

import com.cheung.automanager.controller.vo.KronosVo;

public interface StrategyService {

    Object load() throws Exception;

    void save();

    void delete();

    void get(String name);

    Object exec(KronosVo kronosVo);
}
