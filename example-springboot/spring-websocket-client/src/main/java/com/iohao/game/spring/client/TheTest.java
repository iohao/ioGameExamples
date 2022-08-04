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
package com.iohao.game.spring.client;

import com.iohao.game.action.skeleton.core.CmdKit;
import com.iohao.game.bolt.broker.client.external.bootstrap.message.ExternalMessage;
import com.iohao.game.common.kit.ProtoKit;
import com.iohao.game.spring.common.pb.CatMessage;
import com.iohao.game.spring.common.pb.LoginVerify;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 渔民小镇
 * @date 2022-08-03
 */
@Slf4j
public class TheTest {


    private static void extractedLogin() {
        LoginVerify loginVerify = new LoginVerify();
        loginVerify.age = 273676;
        loginVerify.jwt = "abcd";
        loginVerify.loginBizCode = 1;

        byte[] bytes = ProtoKit.toBytes(loginVerify);

        log.info("bytes : {}", bytes);

        LoginVerify loginVerify1 = ProtoKit.parseProtoByte(bytes, LoginVerify.class);

        log.info("loginVerify1 : {}", loginVerify1);
    }

    public static void main(String[] args) {
//        extractedExternalMessage();
//        extractedLogin();

        CatMessage catMessage = new CatMessage();
        catMessage.cmdCode = 1;
        catMessage.cmdMerge = CmdKit.merge(3, 1);

        byte[] bytes = ProtoKit.toBytes(catMessage);
        log.info("bytes : {}", bytes);

        CatMessage catMessage1 = ProtoKit.parseProtoByte(bytes, CatMessage.class);
        log.info("catMessage1 : {}", catMessage1);
    }

    private static void extractedExternalMessage() {
        // 游戏框架内置的协议， 与游戏前端相互通讯的协议
        ExternalMessage externalMessage = new ExternalMessage();
        // 请求命令类型: 0 心跳，1 业务
        externalMessage.setCmdCode(1);
        externalMessage.setProtocolSwitch(0);
        // 路由
        externalMessage.setCmdMerge(3, 1);
        externalMessage.setResponseStatus(0);


        byte[] bytes = ProtoKit.toBytes(externalMessage);
        log.info("bytes : {}", bytes);

        ExternalMessage externalMessage1 = ProtoKit.parseProtoByte(bytes, ExternalMessage.class);

        log.info("externalMessage1 : {}", externalMessage1);
    }
}
