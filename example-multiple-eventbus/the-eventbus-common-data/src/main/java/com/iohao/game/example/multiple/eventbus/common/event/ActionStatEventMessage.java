/*
 * ioGame
 * Copyright (C) 2021 - present  渔民小镇 （262610965@qq.com、luoyizhu@gmail.com） . All Rights Reserved.
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
package com.iohao.game.example.multiple.eventbus.common.event;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

/**
 * @author 渔民小镇
 * @date 2024-01-07
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ActionStatEventMessage implements Serializable {
    int cmdMerge;
    /** action 执行次数统计 */
    long executeCount;
    /** 总耗时 */
    long totalTime;
    /** action 异常触发次数 */
    long errorCount;
    /** 最大耗时 */
    long maxTime;
    /** 平均耗时 */
    long avgTime;
}
