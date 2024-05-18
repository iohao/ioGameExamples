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
import com.almasb.fxgl.dsl.components.KeepOnScreenComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.iohao.fx.game.component.control.PlayerMoveComponent;
import com.iohao.game.common.kit.RandomKit;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

import static javafx.scene.paint.Color.*;

/**
 * @author 渔民小镇
 * @date 2023-11-23
 */
public class GameFactory implements EntityFactory {
    @Spawns("Player")
    public Entity player(SpawnData data) {
        Long id = data.get("id");
        if (Objects.isNull(id)) {
            id = (long) RandomKit.random(100, 10000);
            data.put("id", id);
        }

        String name = data.get("name");
        if (name.length() > 3) {
            name = name.substring(0, 3);
        }

        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(50);
        rectangle.setHeight(50);
        rectangle.setFill(colors[(int) (id % colors.length)]);

        Label label = new Label();
        label.setText(name);

        StackPane stack = new StackPane(rectangle, label);
        stack.setPrefWidth(50);
        stack.setPrefHeight(50);
        stack.setMaxWidth(50);
        stack.setMinWidth(50);
        stack.setMaxHeight(50);
        stack.setMinHeight(50);

        if (id == MyKit.playerId) {
            DropShadow dropShadow = new DropShadow();
            dropShadow.setOffsetX(10);
            dropShadow.setOffsetY(10);
            rectangle.setEffect(dropShadow);
        }

        PlayerMoveComponent moveComponent = new PlayerMoveComponent();

        return FXGL.entityBuilder(data)
                .viewWithBBox(stack)
                .with(new CollidableComponent(true))
                .with(new KeepOnScreenComponent())
                .with(moveComponent)
                .build();
    }


    Color[] colors = {
            CORAL,
            GOLD,
            BLUEVIOLET,
            BROWN,
            HOTPINK,
            SKYBLUE,
            DODGERBLUE,
            SPRINGGREEN,
            SEAGREEN,
    };
}
