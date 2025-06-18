/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - present double joker （262610965@qq.com） . All Rights Reserved.
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
package com.iohao.game.collect.common;

import com.iohao.game.external.core.config.ExternalGlobalConfig;

/**
 * @author 渔民小镇
 * @date 2022-01-12
 */
public interface GameConfig {
    /** 对外服务器 port */
    int externalPort = ExternalGlobalConfig.externalPort;
    /** 对外服务器 ip */
    String externalIp = "127.0.0.1";
    /** http 升级 websocket 协议地址 */
    String websocketPath = "/websocket";
}
