/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2023 double joker （262610965@qq.com） . All Rights Reserved.
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
package com.iohao.game.spring.common.pb;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.iohao.game.spring.common.SpringGameProtoFile;
import com.iohao.game.widget.light.protobuf.ProtoFileMerge;
import lombok.AccessLevel;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * 登录信息
 *
 * @author 渔民小镇
 * @date 2022-07-27
 */
@ToString
@ProtobufClass
@FieldDefaults(level = AccessLevel.PUBLIC)
@ProtoFileMerge(fileName = SpringGameProtoFile.COMMON_FILE_NAME, filePackage = SpringGameProtoFile.COMMON_FILE_PACKAGE)
public class LoginVerify {
    /** age 测试用的,Integer */
    Integer age;
    /** jwt */
    String jwt;

    /*
     *       注意，这个业务码放这里只是为了方便测试多种情况
     *       交由测试请求端来控制
     *
     *       ------------业务码说明------------------
     *
     *       当业务码为 【0】时 (相当于号已经在线上了，不能重复登录)
     *       如果游戏对外服已经有该玩家的连接了，就抛异常，告诉请求端玩家已经在线。
     *       否则就正常登录成功
     *
     *       当业务码为 【1】时 （相当于顶号）
     *       强制断开之前的客户端连接，并让本次登录成功。
     */
    /** 登录业务码 */
    int loginBizCode;
    /** Long value */
    Long time;

    /** long value */
    long time2;
}
