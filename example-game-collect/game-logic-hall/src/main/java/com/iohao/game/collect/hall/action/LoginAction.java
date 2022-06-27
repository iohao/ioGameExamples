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
package com.iohao.game.collect.hall.action;

import com.iohao.game.collect.hall.HallCmd;
import com.iohao.game.collect.proto.common.LoginVerify;
import com.iohao.game.collect.proto.common.UserInfo;
import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.bolt.broker.client.kit.UserIdSettingKit;
import lombok.extern.slf4j.Slf4j;
import org.jctools.maps.NonBlockingHashMap;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.LongAdder;

/**
 * 登录相关
 *
 * @author 渔民小镇
 * @date 2022-02-02
 */
@Slf4j
@ActionController(HallCmd.cmd)
public class LoginAction {

    final Map<String, Long> userMap = new NonBlockingHashMap<>();

    LongAdder userIdAdder = new LongAdder();

    /**
     * 登录验证
     *
     * @param loginVerify 登录验证pb
     * @param flowContext f
     * @return 用户信息
     */
    @ActionMethod(HallCmd.loginVerify)
    public UserInfo loginVerify(LoginVerify loginVerify, FlowContext flowContext) {
        log.info("loginVerify {} ", loginVerify);

        String jwt = loginVerify.jwt;

        // TODO: 2022/2/6 从数据库中
        Long newUserId = userMap.get(jwt);

        if (Objects.isNull(newUserId)) {
            userIdAdder.increment();

            newUserId = userIdAdder.longValue();
            userMap.put(jwt, newUserId);
        }

        UserInfo userInfo = new UserInfo();
        userInfo.id = newUserId;
        userInfo.name = jwt;

        // 登录的关键代码
        // 具体可参考 https://www.yuque.com/iohao/game/tywkqv
        boolean success = UserIdSettingKit.settingUserId(flowContext, newUserId);

        if (!success) {
            // TODO: 2022/1/19 抛异常码
        }

        userMap.clear();

        return userInfo;
    }


}
