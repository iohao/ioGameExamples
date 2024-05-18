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

import javafx.geometry.Point2D;
import lombok.Getter;

/**
 * @author 渔民小镇
 * @date 2023-11-10
 */
@Getter
public enum Dir {
    /**
     * 运动方向
     */
    UP(new Point2D(0, -1), 0),
    RIGHT(new Point2D(1, 0), 1),
    DOWN(new Point2D(0, 1), 2),
    LEFT(new Point2D(-1, 0), 3);

    final Point2D vector;
    final int value;

    Dir(Point2D vector, int value) {
        this.vector = vector;
        this.value = value;
    }

    public float getX(double speed) {
        return (float) (vector.getX() * speed);
    }

    public float getY(double speed) {
        return (float) (vector.getY() * speed);
    }
}