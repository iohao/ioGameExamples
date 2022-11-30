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
package com.iohao.example.codec;

import com.iohao.game.action.skeleton.core.IoGameGlobalSetting;
import com.iohao.game.action.skeleton.core.codec.JsonDataCodec;
import com.iohao.game.bolt.broker.core.common.IoGameGlobalConfig;
import com.iohao.game.simple.SimpleHelper;

import java.util.List;

/**
 * @author 渔民小镇
 * @date 2022-11-24
 */
public class JsonApplication {
    public static void main(String[] args) {
        // 设置 json 编解码。如果不设置，默认为 jprotobuf
        IoGameGlobalSetting.me().setDataCodec(new JsonDataCodec());

        IoGameGlobalConfig.openLog = false;

        // 游戏对外服端口
        int port = 10100;

        // 逻辑服
        var demoLogicServer = new JsonLogicServer();

        // 启动 对外服、网关服、逻辑服; 并生成游戏业务文档
        SimpleHelper.run(port, List.of(demoLogicServer));

        /*
         * 该示例文档地址
         * https://www.yuque.com/iohao/game/uq2zrltrc7to27bt
         */
    }
}