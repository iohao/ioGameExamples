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
package com.iohao.game.example.external.biz.common;

import com.iohao.game.action.skeleton.core.exception.MsgException;
import com.iohao.game.external.core.broker.client.ext.ExternalBizRegion;
import com.iohao.game.external.core.broker.client.ext.ExternalBizRegionContext;

import java.io.Serializable;

/**
 * @author 渔民小镇
 * @date 2024-07-19
 */
public class OnlineUserExternalBizRegion implements ExternalBizRegion {
    @Override
    public int getBizCode() {
        // 自定义业务码
        return MyExternalBizCode.onlineUser;
    }

    @Override
    public Serializable request(ExternalBizRegionContext regionContext) throws MsgException {

        OnlineUser onlineUser = new OnlineUser();

        // 收集在线玩家的 userId
        regionContext.getUserSessions().forEach(userSession -> {
            long userId = userSession.getUserId();
            onlineUser.getUserIds().add(userId);
        });

        return onlineUser;
    }
}
