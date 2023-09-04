package com.seiryuu.common.appInit;


import com.seiryuu.gameServer.managers.MapManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 系统初始化完毕后
 * 需要被初始化的数据
 * 在这里进行初始化操作
 */
@Slf4j
@Component
public class ApplicationStartup implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) {
        // 初始化地图管理器
        MapManager.INSTANCE.init();
    }
}
