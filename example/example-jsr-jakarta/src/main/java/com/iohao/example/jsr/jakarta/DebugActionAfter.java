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
package com.iohao.example.jsr.jakarta;

import com.iohao.game.action.skeleton.core.flow.ActionAfter;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.action.skeleton.core.flow.attr.FlowAttr;
import com.iohao.game.action.skeleton.core.flow.internal.DefaultActionAfter;
import com.iohao.game.action.skeleton.protocol.ResponseMessage;

/**
 * @author 渔民小镇
 * @date 2024-03-24
 */
public class DebugActionAfter implements ActionAfter {
    ActionAfter actionAfter = new DefaultActionAfter();

    @Override
    public void execute(final FlowContext flowContext) {
        final ResponseMessage response = flowContext.getResponse();
        if (response.hasError()) {
            // 得到异常消息
            String msg = flowContext.option(FlowAttr.msgException);
            // 将异常信息携带到请求端
            response.setValidatorMsg(msg);
        }

        actionAfter.execute(flowContext);
    }
}
