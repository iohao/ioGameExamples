/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - present double joker （262610965@qq.com） . All Rights Reserved.
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
package com.iohao.game.example.hook.action;

import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.bolt.broker.client.kit.UserIdSettingKit;
import com.iohao.game.example.common.msg.login.DemoLoginVerify;
import com.iohao.game.example.common.msg.login.DemoUserInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 渔民小镇
 * @date 2022-05-28
 */
@Slf4j
@ActionController(DemoCmdForHookRoom.cmd)
public class DemoHookRoomAction {

    @ActionMethod(DemoCmdForHookRoom.hello)
    public long hello(FlowContext flowContext) {
        return flowContext.getUserId();
    }

    @ActionMethod(DemoCmdForHookRoom.quitRoom)
    public void quit(FlowContext flowContext) {
        long userId = flowContext.getUserId();
        log.info("收到退出房间消息 userId:{}", userId);
    }

    @ActionMethod(DemoCmdForHookRoom.loginVerify)
    public DemoUserInfo loginVerify(DemoLoginVerify loginVerify, FlowContext flowContext) {

        // 为了方便，这里登录用户的id 写个自身传入 jwt 的 hash
        int newUserId = loginVerify.jwt.hashCode();

        // 登录的关键代码
        // 具体可参考 https://www.yuque.com/iohao/game/tywkqv
        boolean success = UserIdSettingKit.settingUserId(flowContext, newUserId);

        if (!success) {
            log.error("登录错误");
        }

        DemoUserInfo userInfo = new DemoUserInfo();
        userInfo.id = newUserId;
        userInfo.name = loginVerify.jwt;

        return userInfo;
    }
}
