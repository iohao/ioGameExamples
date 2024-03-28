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
package com.iohao.game.exchange.web.kit;

import com.iohao.game.action.skeleton.core.BarSkeleton;
import com.iohao.game.action.skeleton.core.SkeletonAttr;
import com.iohao.game.action.skeleton.core.commumication.BrokerClientContext;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.action.skeleton.core.flow.attr.FlowAttr;
import com.iohao.game.action.skeleton.protocol.HeadMetadata;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.Objects;

/**
 * @author 渔民小镇
 * @date 2024-03-13
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserFlowContext extends FlowContext {

    final HeadMetadata headMetadata;

    private UserFlowContext(long userId, BarSkeleton barSkeleton) {
        this.setBarSkeleton(barSkeleton);

        BrokerClientContext brokerClientContext = barSkeleton.option(SkeletonAttr.brokerClientContext);
        this.option(FlowAttr.brokerClientContext, brokerClientContext);

        var aggregationContext = brokerClientContext.getCommunicationAggregationContext();
        this.option(FlowAttr.aggregationContext, aggregationContext);

        // 设置需要模拟的玩家
        this.headMetadata = new HeadMetadata()
                .setUserId(userId);
    }

    public static UserFlowContext of(long userId, BarSkeleton barSkeleton) {
        Objects.requireNonNull(barSkeleton);
        return new UserFlowContext(userId, barSkeleton);
    }

    @Override
    public BrokerClientContext getBrokerClientContext() {
        return this.getBarSkeleton()
                .option(SkeletonAttr.brokerClientContext);
    }

    @Override
    public HeadMetadata getHeadMetadata() {
        return headMetadata;
    }
}