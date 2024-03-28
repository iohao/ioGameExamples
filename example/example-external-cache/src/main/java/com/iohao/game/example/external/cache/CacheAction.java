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
package com.iohao.game.example.external.cache;

import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.bolt.broker.client.kit.UserIdSettingKit;
import com.iohao.game.example.common.cmd.CacheCmd;
import com.iohao.game.example.common.msg.HelloReq;
import com.iohao.game.example.common.msg.login.DemoLoginVerify;
import com.iohao.game.example.common.msg.login.DemoUserInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author 渔民小镇
 * @date 2024-02-02
 */
@Slf4j
@ActionController(CacheCmd.cmd)
public class CacheAction {
    AtomicLong id = new AtomicLong();


    @ActionMethod(CacheCmd.loginVerify)
    public DemoUserInfo loginVerify(DemoLoginVerify loginVerify, FlowContext flowContext) {

        String jwt = loginVerify.jwt;
        // 真实的用户 id
        long userId = Math.abs(jwt.hashCode());

        // 从数据库中取数据 （通过 jwt）
        DemoUserInfo userInfo = new DemoUserInfo();
        userInfo.id = userId;
        userInfo.name = loginVerify.jwt;

        // channel 中设置用户的真实 userId；
        boolean success = UserIdSettingKit.settingUserId(flowContext, userId);

        log.info("登录 : {}", success);

        return userInfo;
    }

    @ActionMethod(CacheCmd.cacheHere)
    public HelloReq cacheHere(int i) {
        HelloReq newHelloReq = new HelloReq();
        newHelloReq.name = i + " - cacheHere - " + id.incrementAndGet();
        return newHelloReq;
    }

    @ActionMethod(CacheCmd.cacheCustom)
    public HelloReq cacheCustom(int i) {
        HelloReq newHelloReq = new HelloReq();
        newHelloReq.name = i + " - cacheCustom - " + id.incrementAndGet();
        return newHelloReq;
    }

    @ActionMethod(CacheCmd.cacheList)
    public List<HelloReq> cacheList(int i) {

        List<HelloReq> helloList = new ArrayList<>();

        for (int j = 0; j < 2; j++) {
            HelloReq newHelloReq = new HelloReq();
            newHelloReq.name = i + " - cacheList - " + id.incrementAndGet();

            helloList.add(newHelloReq);
        }

        return helloList;
    }
}