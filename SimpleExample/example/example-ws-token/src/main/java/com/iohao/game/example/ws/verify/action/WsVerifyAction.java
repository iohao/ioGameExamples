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
package com.iohao.game.example.ws.verify.action;

import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.example.common.msg.DemoAttachment;
import com.iohao.game.example.common.msg.HelloReq;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 渔民小镇
 * @date 2023-08-04
 */
@Slf4j
@ActionController(WsVerifyCmd.cmd)
public class WsVerifyAction {
    @ActionMethod(WsVerifyCmd.login)
    public HelloReq login(FlowContext flowContext) {
        // 打印元信息
        DemoAttachment attachment = flowContext.getAttachment(DemoAttachment.class);
        log.info("attachment : {}", attachment);

        // 绑定 userId，表示登录
        long userId = attachment.userId;
        boolean success = flowContext.bindingUserId(userId);

        if (success) {
            log.info("登录成功");
        }

        HelloReq newHelloReq = new HelloReq();
        newHelloReq.name = attachment.name;
        return newHelloReq;
    }
}
