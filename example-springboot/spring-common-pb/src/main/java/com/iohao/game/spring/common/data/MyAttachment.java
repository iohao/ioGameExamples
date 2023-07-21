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
package com.iohao.game.spring.common.data;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.iohao.game.core.common.client.Attachment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * 自定义一个元信息类，实现 Attachment 元附加信息接口
 *
 * @author 渔民小镇
 * @date 2023-01-14
 */
@ToString
@ProtobufClass
@FieldDefaults(level = AccessLevel.PUBLIC)
public class MyAttachment implements Attachment {
    @Getter
    long userId;
    String nickname;

    String serverId;
    String playerId;
}
