package com.seiryuu.gameServer.common.exception;

import com.iohao.game.action.skeleton.core.exception.MsgExceptionInfo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 * 断言 + 异常机制
 * @author 渔民小镇
 * @date 2022-07-09
 */
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum SpringGameCodeEnum implements MsgExceptionInfo {

    /** 学校最大等级 */
    levelMax(101, "学校等级超出"),

    /** 等级不够 */
    vipLevelEnough(102, "VIP 等级不够"),

    /** 号已经在线上，不允许重复登录 */
    accountOnline(103, "不可重复登录"),

    /** 登录异常 */
    loginError(104, "登录异常"),

    ;

    /** 消息码 */
    final int code;
    /** 消息模板 */
    final String msg;

    SpringGameCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}