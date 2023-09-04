package com.seiryuu.gameServer.managers;

import cn.hutool.core.convert.Convert;
import com.seiryuu.gameServer.domain.map.GameMap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Seiryuu
 * @description 地图管理器
 * @since 2023-05-08 13:50
 */
public enum MapManager {

    INSTANCE;

    public final Map<String, GameMap> maps = new ConcurrentHashMap<>();

    /**
     * 获取当前地图
     *
     * @param mapId
     * @return
     */
    public GameMap getGameMap(String mapId) {
        return maps.get(mapId);
    }

    /**
     * 周期性刷怪
     * 每个地图执行刷怪逻辑
     */
    public void updateAllMap() {
        for (String mapId : maps.keySet()) {
            maps.get(mapId).update();
        }
    }

    /**
     * 初始化地图以及网格
     * TODO 从数据库加载地图信息 配置等信息 设置地图配置 根据地图大小生成不同的格子数量
     */
    public void init() {
        for (int i = 1; i <= 2; i++) {
            String mapId = Convert.toStr(i);
            GameMap gameMap = new GameMap(mapId);

            maps.put(mapId, gameMap);
        }
    }
}