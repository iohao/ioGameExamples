/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2023 double joker （262610965@qq.com） . All Rights Reserved.
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
package com.iohao.game.action.skeleton.core.flow;

import com.iohao.game.spring.common.data.MyAttachment;

import java.util.Objects;

/**
 * @author 渔民小镇
 * @date 2022-08-20
 */
public class MyFlowContext extends FlowContext {
    public MyAttachment attachment;

    public String getServerId() {
        initAttachment();
        if (attachment == null) {
            return null;
        }
        return attachment.serverId;
    }

    public String getPlayerId() {
        initAttachment();
        if (attachment == null) {
            return null;
        }

        return attachment.playerId;
    }

    private void initAttachment() {
        if (Objects.isNull(attachment)) {
            this.attachment = this.getAttachment(MyAttachment.class);
        }
    }

    public String hello() {
        // 在 MyFlowContext 中，扩展的方法
        return "MyFlowContext hello";
    }

    public String getNickname() {
        MyAttachment attachment = this.getAttachment(MyAttachment.class);
        return attachment.nickname;
    }
}
