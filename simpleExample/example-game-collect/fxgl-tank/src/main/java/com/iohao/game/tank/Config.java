package com.iohao.game.tank;

import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * @author 渔民小镇
 * @date 2022-03-06
 */
public class Config {
    /**
     * 顶级的子弹可以摧毁树木, 可以打石头墙
     */
    public static final int PLAYER_BULLET_MAX_LEVEL = 2;
    public static final int  MAX_ENEMY_NUM = 20;

    public static final int PLAYER_HEALTH = 5;
    public static final double PLAYER_BULLET_SPEED = 420;
    public static final double ENEMY_BULLET_SPEED = 450;
    public static final Duration PLAYER_SHOOT_DELAY = Duration.seconds(0.3);
    public static final Duration ENEMY_SHOOT_DELAY = Duration.seconds(0.35);
    /**
     * 保护罩保护的无敌时间
     */
    public static final Duration HELMET_TIME = Duration.seconds(12);
    /**
     * 定时道具.敌人停止行动的时间
     */
    public static final Duration STOP_MOVE_TIME = Duration.seconds(10);
    /**
     * 道具出现的时间
     */
    public static final Duration REWARD_SHOW_TIME = Duration.seconds(17);
    /**
     * 道具从出现到闪烁提醒的时间
     */
    public static final Duration REWARD_NOTIFY_TIME = Duration.seconds(12);
    /**
     * spade 基地保护时间
     */
    public static final Duration SPADE_TIME = Duration.seconds(15);

    public static final Duration SPADE_NOTIFY_TIME = Duration.seconds(12);

    public static final Duration BOMB_ANIME_TIME = Duration.seconds(0.5);

    public static final Duration GENERATE_ENEMY_TIME = Duration.seconds(8);

    public static final Color BG_GARY = Color.web("#666666");
}
