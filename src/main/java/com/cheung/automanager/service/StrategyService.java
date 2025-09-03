package com.cheung.automanager.service;

public interface StrategyService {

    Object load() throws Exception;

    void save();

    void delete();

    void get(String name);

    Object exec(String shell, boolean sync);
}
