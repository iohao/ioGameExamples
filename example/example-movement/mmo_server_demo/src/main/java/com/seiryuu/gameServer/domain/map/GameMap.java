package com.seiryuu.gameServer.domain.map;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import com.iohao.game.action.skeleton.core.CmdInfo;
import com.iohao.game.bolt.broker.core.client.BrokerClientHelper;
import com.seiryuu.gameServer.common.cmd.ActionCmd;
import com.seiryuu.gameServer.protocol.map.EnterMapProto;
import com.seiryuu.gameServer.protocol.map.LeaveMapProto;
import com.seiryuu.gameServer.protocol.player.CharacterProto;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author Seiryuu
 * @description 游戏地图类
 * @since 2023-05-09 7:46
 */
@Slf4j
@Data
public class GameMap {

    /**
     * 地图id
     */
    private String mapId;

    public GameMap(String mapId) {
        this.mapId = mapId;
    }


    // 当前地图进入的玩家
    private final Map<String, CharacterProto> mapCharacterMap = new ConcurrentHashMap<>();

    /**
     * 角色矩阵 当前角色在哪一个格子
     *
     * @return
     */
    // private final Map<String, String> characterMatrix = new ConcurrentHashMap<>();

    /**
     * 获取当前地图中的角色id
     *
     * @return
     */
    public List<Long> currentMapCharacterIds(String characterId) {
        return this.mapCharacterMap.keySet().stream()
                // .filter(id -> !StrUtil.equals(id, characterId))
                .map(Convert::toLong)
                .collect(Collectors.toList());
    }

    /**
     * 角色进入地图
     *
     * @param characterProto
     */
    public void characterEnter(CharacterProto characterProto) {
        // 当前选择角色 添加到当前地图管理
        if (BeanUtil.isEmpty(mapCharacterMap.get(characterProto.getCharacterId()))) {
            this.mapCharacterMap.put(characterProto.characterId, characterProto);
        }

        // 当前地图玩家不为空 广播给这些玩家
        if (MapUtil.isNotEmpty(mapCharacterMap)) {
            // 把当前进入游戏的角色 广播给当前地图中 一定范围内的其他角色 有玩家进入地图
            BrokerClientHelper.getBroadcastContext().broadcast(
                    CmdInfo.getCmdInfo(ActionCmd.cmd, ActionCmd.enterGame),
                    EnterMapProto.builder()
                            .characterId(characterProto.characterId)
                            .mapPostX(characterProto.mapPostX)
                            .mapPostY(characterProto.mapPostY)
                            .mapPostZ(characterProto.mapPostZ)
                            .orientation(characterProto.orientation)
                            .build(),
                    currentMapCharacterIds(characterProto.characterId)
            );
        }

        // 其他玩家列表
        List<CharacterProto> otherCharacterList = new ArrayList<>();

        /*
          循环地图中的每一个角色
          广播有新角色进入
         */
        // for (String characterId : mapCharacterMap.keySet()) {
        //     CharacterProto mapCharacter = mapCharacterMap.get(characterId);
        //     otherCharacterList.add(mapCharacter);
        //
        //     if (!StrUtil.equals(characterId, mapCharacter.getCharacterId()) && StrUtil.equals(gridId, mapCharacter.getGridId())) {
        //         // 发送给每个玩家 一个附近玩家的列表
        //         SyncMapPlayerProto syncMapPlayerProto = SyncMapPlayerProto.builder().playerCharacterList(otherCharacterList).build();
        //
        //     }
        // }

        log.info("[角色 {}]：进入地图：{}，地图在线人数：{}", characterProto.characterId, mapId, mapCharacterMap.size());
    }

    /**
     * 角色离开地图
     */
    public void characterLeave(String characterId) {
        this.mapCharacterMap.remove(characterId);

        // 角色离开 广播给地图角色
        BrokerClientHelper.getBroadcastContext().broadcast(
                CmdInfo.getCmdInfo(ActionCmd.cmd, ActionCmd.leaveMap),
                LeaveMapProto.builder().characterId(characterId).build(),
                currentMapCharacterIds(characterId)
        );
    }

    /**
     * 角色进入地图后 广播给玩家
     */
    public void syncMapPlayer(CharacterProto characterProto) {
    }

    /**
     * 地图刷怪 等操作
     */
    public void update() {
        log.info("[{} 地图] -> 刷怪！", this.mapId);
    }
}
