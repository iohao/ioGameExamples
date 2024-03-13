/*
 * ioGame
 * Copyright (C) 2021 - 2024  渔民小镇 （262610965@qq.com、luoyizhu@gmail.com） . All Rights Reserved.
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
package com.iohao.game.exchange.web;

import com.iohao.game.action.skeleton.core.BarSkeleton;
import com.iohao.game.action.skeleton.core.BarSkeletonBuilderParamConfig;
import com.iohao.game.bolt.broker.client.AbstractBrokerClientStartup;
import com.iohao.game.bolt.broker.core.client.BrokerClient;
import com.iohao.game.bolt.broker.core.client.BrokerClientBuilder;
import com.iohao.game.bolt.broker.core.common.processor.listener.BrokerClientListener;
import com.iohao.game.bolt.broker.core.message.BrokerClientModuleMessage;
import com.iohao.game.exchange.web.kit.MyKit;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 渔民小镇
 * @date 2024-03-13
 */
@Slf4j
public class GameManagerLogicServer extends AbstractBrokerClientStartup {
    @Override
    public BarSkeleton createBarSkeleton() {
        return new BarSkeletonBuilderParamConfig()
                .createBuilder()
                .build();
    }

    @Override
    public BrokerClientBuilder createBrokerClientBuilder() {
        BrokerClientBuilder builder = BrokerClient.newBuilder();
        builder.appName("GM 逻辑服");

        extractedBrokerClientListener(builder);

        return builder;
    }

    private void extractedBrokerClientListener(BrokerClientBuilder builder) {
        // 演示 - BrokerClient 监听
        builder.addListener(new BrokerClientListener() {
            @Override
            public void onlineExternal(BrokerClientModuleMessage otherModuleMessage, BrokerClient client) {
                log.info("上线 - 游戏对外服 {} {} {}", otherModuleMessage.getTag(), otherModuleMessage.getId(), otherModuleMessage);
            }

            @Override
            public void offlineExternal(BrokerClientModuleMessage otherModuleMessage, BrokerClient client) {
                log.info("下线 - 游戏对外服 {} {}", otherModuleMessage.getTag(), otherModuleMessage.getId());
            }

            @Override
            public void onlineLogic(BrokerClientModuleMessage otherModuleMessage, BrokerClient client) {
                log.info("上线 - 游戏逻辑服 {} {}", otherModuleMessage.getTag(), otherModuleMessage.getId());
            }

            @Override
            public void offlineLogic(BrokerClientModuleMessage otherModuleMessage, BrokerClient client) {
                log.info("下线 - 游戏逻辑服 {} {}", otherModuleMessage.getTag(), otherModuleMessage.getId());
            }
        });
    }

    @Override
    public void startupSuccess(BrokerClient brokerClient) {
        // 保存一下业务框架和逻辑服的引用
        MyKit.brokerClient = brokerClient;
        MyKit.barSkeleton = brokerClient.getBarSkeleton();
    }
}