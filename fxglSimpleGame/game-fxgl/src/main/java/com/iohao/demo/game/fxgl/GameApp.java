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

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.iohao.fx.game.component.control.PlayerMoveComponent;
import com.iohao.game.sdk.net.internal.config.ClientUserConfigs;
import javafx.scene.input.KeyCode;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;

import static com.almasb.fxgl.dsl.FXGL.*;

/**
 * @author 渔民小镇
 * @date 2023-11-23
 */
@Slf4j
public class GameApp extends GameApplication {
    protected boolean loadBGM = true;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initSettings(GameSettings settings) {
        // 关闭网络请求日志
        ClientUserConfigs.closeLog();

        // 启动与服务器的连接
        MyKit.startup();

        settings.setWidth(450);
        settings.setHeight(600);
        settings.setTitle("ioGame 网络游戏");
        settings.setVersion("0.1");
        settings.setGameMenuEnabled(true);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("score", 0);
    }

    @Override
    protected void initUI() {
        var controller = new GameController();
        var ui = FXGL.getAssetLoader().loadUI("demo_ui.fxml", controller);

        getGameScene().addUI(ui);

        String format = "加入房间后，WASD 移动； score: %d";
        controller.getPlayerName().textProperty().bind(getip("score").asString(format));
    }

    @Override
    protected void initGame() {
        if (loadBGM) {
            loopBGM("bgm.mp3");
        }

        // factory
        FXGL.getGameWorld().addEntityFactory(new GameFactory());
    }

    @Override
    protected void initInput() {

        onKey(KeyCode.A, () -> {
            Optional.ofNullable(MyKit.myFightPlayer.playerMoveComponent).ifPresent(PlayerMoveComponent::left);
        });

        onKey(KeyCode.D, () -> {
            Optional.ofNullable(MyKit.myFightPlayer.playerMoveComponent).ifPresent(PlayerMoveComponent::right);

        });

        onKey(KeyCode.W, () -> {
            Optional.ofNullable(MyKit.myFightPlayer.playerMoveComponent).ifPresent(PlayerMoveComponent::up);

        });

        onKey(KeyCode.S, () -> {
            Optional.ofNullable(MyKit.myFightPlayer.playerMoveComponent).ifPresent(PlayerMoveComponent::down);
        });
    }
}
