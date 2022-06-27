/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2022 double joker （262610965@qq.com） . All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License..
 */
package com.iohao.game.collect.proto.tank;

import com.baidu.bjf.remoting.protobuf.annotation.EnableZigZap;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.iohao.game.collect.proto.GameProtoFile;
import com.iohao.game.widget.light.protobuf.ProtoFileMerge;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.Map;

/**
 * 玩家的坦克
 *
 * @author 渔民小镇
 * @date 2022-01-15
 */
@Setter
@ProtobufClass
@EnableZigZap
@FieldDefaults(level = AccessLevel.PUBLIC)
@ToString
@ProtoFileMerge(fileName = GameProtoFile.TANK_FILE_NAME, filePackage = GameProtoFile.TANK_FILE_PACKAGE)
public class TankPlayer {
    /** 玩家id */
    long id;

    /** 坦克 血条 */
    long hp;

    /** 坦克 所属队伍 */
    int team;

    /** 坦克 速度 */
    int speed;

    /** 坦克 皮肤 */
    int skin;

    /** 坦克位置 */
    TankLocation tankLocation;

    /** 坦克子弹: key: 子弹 id 1 玩具弹, 2 雪弹; value : 数量 */
    Map<Integer, Integer> tankBulletMap;
}
