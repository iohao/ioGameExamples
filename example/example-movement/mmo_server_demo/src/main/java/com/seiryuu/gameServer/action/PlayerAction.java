package com.seiryuu.gameServer.action;

import cn.hutool.core.convert.Convert;
import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.core.CmdInfo;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.bolt.broker.client.kit.UserIdSettingKit;
import com.iohao.game.bolt.broker.core.client.BrokerClientHelper;
import com.iohao.game.common.kit.ExecutorKit;
import com.iohao.game.external.core.kit.ExternalKit;
import com.iohao.game.external.core.message.ExternalMessage;
import com.seiryuu.gameServer.common.cmd.ActionCmd;
import com.seiryuu.gameServer.domain.map.GameMap;
import com.seiryuu.gameServer.managers.MapManager;
import com.seiryuu.gameServer.protocol.map.SyncMapPlayerProto;
import com.seiryuu.gameServer.protocol.movement.PlayerMoveProto;
import com.seiryuu.gameServer.protocol.player.CharacterProto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Seiryuu
 * @description
 * @since 2023-08-29 8:17
 */
@Slf4j
@Component
@ActionController(ActionCmd.cmd)
public class PlayerAction {


    /**
     * 玩家进入游戏
     *
     * @return
     */
    @ActionMethod(ActionCmd.enterGame)
    public void enterGame(FlowContext flowContext, CharacterProto characterProto) {
        String characterId = characterProto.getCharacterId();
        Long mapId = characterProto.mapId;

        // 设置用户id
        UserIdSettingKit.settingUserId(flowContext, Convert.toLong(characterId));

        ExecutorKit.newSingleScheduled("enterMapBroadcast -> " + mapId).submit(() ->
                MapManager.INSTANCE.getGameMap(Convert.toStr(mapId)).characterEnter(characterProto));
    }

    /**
     * 玩家移动
     *
     * @param moveProto
     * @return
     */
    @ActionMethod(ActionCmd.move)
    public void playerMove(PlayerMoveProto moveProto) {

        ExecutorKit.newSingleScheduled("playerMoveBroadcast").submit(() -> {
                    GameMap gameMap = MapManager.INSTANCE.getGameMap("1");

                    CharacterProto characterProto = gameMap.getMapCharacterMap().get(moveProto.characterId);
                    characterProto.mapPostX = moveProto.x;
                    characterProto.mapPostY = moveProto.y;
                    characterProto.mapPostZ = moveProto.z;
                    characterProto.orientation = moveProto.r;
                    gameMap.getMapCharacterMap().put(characterProto.characterId, characterProto);

                    // 有玩家 移动 广播
                    BrokerClientHelper.getBroadcastContext().broadcast(
                            CmdInfo.getCmdInfo(ActionCmd.cmd, ActionCmd.move),
                            moveProto,
                            gameMap.currentMapCharacterIds(characterProto.characterId)
                    );
                }
        );


    }

    /**
     * 同步周围玩家
     *
     * @return
     */
    @ActionMethod(ActionCmd.syncPlayer)
    public ExternalMessage syncPlayer() {
        // 方便测试 先写死了
        GameMap gameMap = MapManager.INSTANCE.getGameMap("1");
        List<CharacterProto> collect = new ArrayList<>(gameMap.getMapCharacterMap().values());
        SyncMapPlayerProto syncMapPlayerProto = SyncMapPlayerProto.builder().playerCharacterList(collect).build();
        return ExternalKit.createExternalMessage(ActionCmd.cmd, ActionCmd.syncPlayer, syncMapPlayerProto);
    }
}
