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
package com.iohao.game.spring.logic.core;

import com.iohao.game.action.skeleton.core.codec.DataCodec;
import com.iohao.game.common.kit.ProtoKit;

import java.util.Objects;

/**
 * @author 渔民小镇
 * @date 2023-09-05
 */
@SuppressWarnings("unchecked")
public class MyProtoDataCodec implements DataCodec {
    //    byte[] EMPTY_BYTES = CommonConst.EMPTY_BYTES;
    byte[] EMPTY_BYTES = new byte[0];

    @Override
    public byte[] encode(Object data) {
        return ProtoKit.toBytes(data);
    }

    @Override
    public <T> T decode(byte[] data, Class<?> dataClass) {

        if (Objects.isNull(data)) {
            data = EMPTY_BYTES;
        }

        return (T) ProtoKit.parseProtoByte(data, dataClass);
    }
}
