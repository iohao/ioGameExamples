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
package com.iohao.fx.game.component.control;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.component.Component;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Objects;

/**
 * @author 渔民小镇
 * @date 2023-11-10
 */
@FieldDefaults(level = AccessLevel.PROTECTED)
public class PlayerMoveComponent extends Component {
    final Vec2 velocity = new Vec2();
    @Setter
    MoveChangeListener moveChangeListener;

    /**
     * 为了防止出现斜向上,斜向下等角度的移动,
     */
    boolean movedThisFrame = false;
    double speed = 0;
    Dir moveDir = Dir.RIGHT;
    double playerSpeed = 500;

    @Override
    public void onUpdate(double tpf) {
        speed = tpf * playerSpeed;
        movedThisFrame = false;
    }

    public void right() {
        if (movedThisFrame) {
            return;
        }

        movedThisFrame = true;
        moveDir = Dir.RIGHT;
        move();
    }

    public void left() {
        if (movedThisFrame) {
            return;
        }

        movedThisFrame = true;
        moveDir = Dir.LEFT;
        move();
    }

    public void down() {
        if (movedThisFrame) {
            return;
        }

        movedThisFrame = true;
        moveDir = Dir.DOWN;
        move();
    }

    public void up() {
        if (movedThisFrame) {
            return;
        }

        movedThisFrame = true;
        moveDir = Dir.UP;
        move();
    }

    protected void move() {
        if (Objects.isNull(getEntity()) || !getEntity().isActive()) {
            return;
        }

        velocity.set(moveDir.getX(speed), moveDir.getY(speed));
        entity.translate(velocity);
        velocity.normalizeLocal();

        if (Objects.nonNull(moveChangeListener)) {
            moveChangeListener.move(moveDir);
        }
    }

    public interface MoveChangeListener {
        void move(Dir dir);
    }
}
