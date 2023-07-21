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
package com.iohao.game.example.common;

import com.iohao.game.action.skeleton.core.commumication.InvokeModuleContext;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import lombok.experimental.UtilityClass;

/**
 * @author 渔民小镇
 * @date 2022-08-20
 */
@UtilityClass
public class MyBrokerClientHelper {

    /**
     * 游戏逻辑服与游戏逻辑服之间的通讯上下文
     *
     * @return InvokeModuleContext
     */
    public InvokeModuleContext getInvokeModuleContext(FlowContext flowContext) {
        return MyInvokeModuleContext.of(flowContext);
    }
}