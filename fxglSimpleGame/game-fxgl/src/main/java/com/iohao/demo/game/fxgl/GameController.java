/*
 * ioGame
 * Copyright (C) 2021 - 2023  渔民小镇 （262610965@qq.com、luoyizhu@gmail.com） . All Rights Reserved.
 * # iohao.com . 渔民小镇
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.iohao.demo.game.fxgl;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.ui.UIController;
import com.iohao.demo.game.common.MyCmd;
import com.iohao.game.sdk.command.RequestCommand;
import com.iohao.game.sdk.kit.CmdKit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.Map;

/**
 * @author 渔民小镇
 * @date 2023-11-23
 */
@Slf4j
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GameController implements UIController {
    @FXML
    Label playerName;

    @FXML
    void onJoinRoom(ActionEvent event) {
        if (MyKit.joinRoom.compareAndSet(false, true)) {
            FXGL.play("shoot2.wav");

            int cmdMerge = CmdKit.merge(MyCmd.cmd, MyCmd.enterRoom);
            RequestCommand.of(cmdMerge)
                    .setTitle("joinRoom")
                    .setRequestData(() -> MyKit.myFightPlayer.playerInfo)
                    .execute();
        }
    }

    @FXML
    void onQuitRoom(ActionEvent event) {
        if (MyKit.joinRoom.compareAndSet(true, false)) {
//            FXGL.getAssetLoader().loadSound("lose_life.wav");
            FXGL.play("lose_life.wav");

            RequestCommand
                    .of(CmdKit.merge(MyCmd.cmd, MyCmd.quitRoom))
                    .setTitle("quitRoom")
                    .execute();

            Map<Long, MyKit.FightPlayer> entityMap = MyKit.entityMap;

            Iterator<Long> iterator = entityMap.keySet().iterator();
            while (iterator.hasNext()) {
                Long playerId = iterator.next();
                MyKit.FightPlayer fightPlayer = entityMap.get(playerId);
                fightPlayer.player.removeFromWorld();
                iterator.remove();
            }
        }
    }

    @Override
    public void init() {
        System.out.println("hello");
    }
}