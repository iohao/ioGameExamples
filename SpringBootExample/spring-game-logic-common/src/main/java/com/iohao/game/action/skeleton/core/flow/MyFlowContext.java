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
package com.iohao.game.action.skeleton.core.flow;

import com.iohao.game.spring.common.data.MyAttachment;

import java.util.Objects;

/**
 * @author 渔民小镇
 * @date 2022-08-20
 */
public class MyFlowContext extends FlowContext {
    MyAttachment attachment;

    @Override
    @SuppressWarnings("unchecked")
    public MyAttachment getAttachment() {
        if (Objects.isNull(attachment)) {
            this.attachment = this.getAttachment(MyAttachment.class);
        }

        return this.attachment;
    }

    public String hello() {
        // 在 MyFlowContext 中，扩展的方法
        return "MyFlowContext hello";
    }

    public String getNickname() {
        MyAttachment attachment = this.getAttachment();
        return attachment.nickname;
    }
}
