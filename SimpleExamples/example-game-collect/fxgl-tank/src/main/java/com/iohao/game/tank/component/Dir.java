package com.iohao.game.tank.component;

import javafx.geometry.Point2D;

/**
 * @author 渔民小镇
 * @date 2022-03-06
 */
public enum Dir {
    /**
     *
     */
    UP(new Point2D(0, -1)),
    RIGHT(new Point2D(1, 0)),
    DOWN(new Point2D(0, 1)),
    LEFT(new Point2D(-1, 0));

    public Point2D vector;

    Dir(Point2D vector) {
        this.vector = vector;
    }

}