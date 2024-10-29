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
package com.iohao.game.exchange.web;

import com.iohao.game.action.skeleton.core.BarMessageKit;
import com.iohao.game.action.skeleton.core.DataCodecKit;
import com.iohao.game.action.skeleton.core.commumication.InvokeModuleContext;
import com.iohao.game.action.skeleton.protocol.HeadMetadata;
import com.iohao.game.action.skeleton.protocol.RequestMessage;
import com.iohao.game.action.skeleton.protocol.wrapper.StringValue;
import com.iohao.game.bolt.broker.client.kit.ExternalCommunicationKit;
import com.iohao.game.bolt.broker.core.client.BrokerClient;
import com.iohao.game.exchange.common.ExchangeAttachment;
import com.iohao.game.exchange.common.ExchangeCmd;
import com.iohao.game.exchange.web.kit.MyKit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author 渔民小镇
 * @date 2024-04-18
 */
@Slf4j
@RestController
@RequestMapping("other")
public class OtherController {
    static final AtomicLong msgId = GameManagerController.msgId;
    /** 为了方便测试，这里指定一个 userId 来模拟玩家 */
    static final long userId = GameManagerController.userId;

    @GetMapping("/notice")
    public String notice() {
        log.info("other notice");
        // 使用协议碎片特性 https://www.yuque.com/iohao/game/ieimzn
        StringValue data = StringValue.of("other GM web msg " + msgId.incrementAndGet());
        // 模拟请求 : 路由 - 业务数据
        RequestMessage requestMessage = BarMessageKit.createRequestMessage(ExchangeCmd.of(ExchangeCmd.notice), data);

        // 设置需要模拟的玩家
        HeadMetadata headMetadata = requestMessage.getHeadMetadata();
        headMetadata.setUserId(userId);

        // 从游戏对外服中获取一些用户（玩家的）自身的数据，如元信息、所绑定的游戏逻辑服 ...等
        Optional<HeadMetadata> headMetadataOptional = ExternalCommunicationKit.employHeadMetadata(requestMessage);

        if (headMetadataOptional.isPresent()) {
            // 发起模拟请求
            extractedRequestLogic(requestMessage);

            // 打印从游戏对外服获取的元信息
            byte[] attachmentData = headMetadata.getAttachmentData();
            ExchangeAttachment attachment = DataCodecKit.decode(attachmentData, ExchangeAttachment.class);
            return "other notice 玩家的元信息: %s - %s".formatted(attachment, msgId.get());
        } else {
            return "other notice 玩家 %s 不在线，无法获取玩家的元信息 - %s".formatted(userId, msgId.get());
        }
    }

    private void extractedRequestLogic(RequestMessage requestMessage) {
        // 向逻辑服发送请求，该模拟请求具备了玩家的元信息。
        BrokerClient brokerClient = MyKit.brokerClient;
        InvokeModuleContext invokeModuleContext = brokerClient.getInvokeModuleContext();
        invokeModuleContext.invokeModuleVoidMessage(requestMessage);
    }
}
