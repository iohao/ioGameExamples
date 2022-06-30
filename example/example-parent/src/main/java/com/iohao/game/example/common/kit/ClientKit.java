/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2022 double joker （262610965@qq.com） . All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.iohao.game.example.common.kit;

import com.iohao.game.action.skeleton.core.flow.codec.ProtoDataCodec;
import com.iohao.game.bolt.broker.client.external.bootstrap.message.ExternalMessage;
import com.iohao.game.bolt.broker.client.external.bootstrap.message.ExternalMessageCmdCode;
import com.iohao.game.common.kit.ProtoKit;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;

/**
 * @author 渔民小镇
 * @date 2022-06-30
 */

@UtilityClass
public class ClientKit {
    public byte[] createExternalMessageBytes(int cmd, int subCmd, Object dataPb) {
        ExternalMessage externalMessage = createExternalMessage(cmd, subCmd, dataPb);

        // 转为字节
        byte[] bytes = ProtoKit.toBytes(externalMessage);
        // 发送数据到游戏服务器
        return bytes;
    }

    public ExternalMessage createExternalMessage(int cmd, int subCmd, Object dataPb) {
        // 游戏框架内置的协议， 与游戏前端相互通讯的协议
        ExternalMessage externalMessage = new ExternalMessage();
        // 请求命令类型: 0 心跳，1 业务
        externalMessage.setCmdCode(ExternalMessageCmdCode.biz);
        // 路由
        externalMessage.setCmdMerge(cmd, subCmd);
        // 业务数据
        byte[] data = ProtoDataCodec.me().encode(dataPb);
        // 业务数据
        externalMessage.setData(data);
        return externalMessage;
    }
}
