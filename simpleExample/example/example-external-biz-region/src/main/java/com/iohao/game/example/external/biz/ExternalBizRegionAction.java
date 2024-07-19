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
package com.iohao.game.example.external.biz;

import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.action.skeleton.protocol.external.ResponseCollectExternalItemMessage;
import com.iohao.game.action.skeleton.protocol.external.ResponseCollectExternalMessage;
import com.iohao.game.bolt.broker.client.kit.UserIdSettingKit;
import com.iohao.game.example.common.cmd.ExternalBizRegionCmd;
import com.iohao.game.example.external.biz.common.MyExternalBizCode;
import com.iohao.game.example.external.biz.common.OnlineUser;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author 渔民小镇
 * @date 2024-07-19
 */
@Slf4j
@ActionController(ExternalBizRegionCmd.cmd)
public class ExternalBizRegionAction {
    AtomicLong userIdInc = new AtomicLong();

    @ActionMethod(ExternalBizRegionCmd.loginVerify)
    public boolean loginVerify(FlowContext flowContext) {
        long userId = userIdInc.incrementAndGet();
        // 登录成功
        return UserIdSettingKit.settingUserId(flowContext, userId);
    }

    @ActionMethod(ExternalBizRegionCmd.listOnlineUser)
    public List<Long> listOnlineUser(FlowContext flowContext) {
        ResponseCollectExternalMessage collectExternalMessage = flowContext
                .invokeExternalModuleCollectMessage(MyExternalBizCode.onlineUser);

        List<Long> userIdList = new ArrayList<>();
        // 打印从各游戏对外服中获取的数据
        for (ResponseCollectExternalItemMessage itemMessage : collectExternalMessage.getMessageList()) {
            // OnlineUserExternalBizRegion 所返回的数据
            OnlineUser onlineUser = itemMessage.getData();

            List<Long> userIds = onlineUser.getUserIds();
            log.info("userIds : {}", userIds);

            userIdList.addAll(userIds);
        }

        return userIdList;
    }
}
