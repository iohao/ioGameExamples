/*
 * ioGame
 * Copyright (C) 2021 - 2023  渔民小镇 （262610965@qq.com、luoyizhu@gmail.com） . All Rights Reserved.
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
package com.iohao.game.example.interaction.same.hall.action;

import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.core.CmdInfo;
import com.iohao.game.action.skeleton.core.commumication.InvokeModuleContext;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.action.skeleton.protocol.ResponseMessage;
import com.iohao.game.action.skeleton.protocol.collect.ResponseCollectItemMessage;
import com.iohao.game.action.skeleton.protocol.collect.ResponseCollectMessage;
import com.iohao.game.action.skeleton.protocol.external.ResponseCollectExternalMessage;
import com.iohao.game.bolt.broker.core.client.BrokerClientHelper;
import com.iohao.game.core.common.client.Attachment;
import com.iohao.game.example.common.msg.RoomNumMsg;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 使用示例，关于 FlowContext 通信能力，提供同步、异步、异步回调 ...等，相关示例
 *
 * @author 渔民小镇
 * @date 2023-12-25
 */
@Slf4j
public class FlowContextExample {
    CmdInfo cmdInfo = CmdInfo.of(1, 1);
    int bizCode = 1;

    YourData yourData = new YourData();
    FlowContext flowContext;

    /**
     * 通讯方式特点：单个游戏逻辑服之间的交互
     * <pre>
     *     适合不需要接收响应的业务
     *     无阻塞
     *
     *     <a href="https://www.yuque.com/iohao/game/anguu6">文档</a>
     * </pre>
     */
    void invokeModuleVoidMessage() {
        // 路由
        flowContext.invokeModuleVoidMessage(cmdInfo);
        // 路由、请求参数
        flowContext.invokeModuleVoidMessage(cmdInfo, yourData);
    }

    /**
     * 通讯方式特点：单个游戏逻辑服之间的交互。
     * <pre>
     *     可接收响应
     *     提供同步、异步回调、异步的编码风格
     *
     *     <a href="https://www.yuque.com/iohao/game/anguu6">文档</a>
     * </pre>
     */
    void invokeModuleMessage() {
        // 同步
        invokeModuleMessageSync();
        // 异步回调
        // --- 此回调写法，具备全链路调用日志跟踪 ---
        invokeModuleMessageAsync();
        // 异步
        invokeModuleMessageFuture();

        // 得到业务框架对应的 InvokeModuleContext
        InvokeModuleContext invokeModuleContext = flowContext.getInvokeModuleContext();
        // 如果一个进程中启动了多个游戏逻辑服，随机取一个
        InvokeModuleContext invokeModuleContext1 = BrokerClientHelper.getInvokeModuleContext();
    }

    void invokeModuleMessageSync() {
        // 路由
        ResponseMessage responseMessage = flowContext.invokeModuleMessage(cmdInfo);
        RoomNumMsg roomNumMsg = responseMessage.getData(RoomNumMsg.class);
        log.info("同步调用 : {}", roomNumMsg.roomCount);

        // 路由、请求参数
        ResponseMessage responseMessage2 = flowContext.invokeModuleMessage(cmdInfo, yourData);
        RoomNumMsg roomNumMsg2 = responseMessage2.getData(RoomNumMsg.class);
        log.info("同步调用 : {}", roomNumMsg2.roomCount);
    }

    void invokeModuleMessageAsync() {
        // --- 此回调写法，具备全链路调用日志跟踪 ---
        // 路由、回调
        flowContext.invokeModuleMessageAsync(cmdInfo, responseMessage -> {
            RoomNumMsg roomNumMsg = responseMessage.getData(RoomNumMsg.class);
            log.info("异步回调 : {}", roomNumMsg.roomCount);
        });

        // 路由、请求参数、回调
        flowContext.invokeModuleMessageAsync(cmdInfo, yourData, responseMessage -> {
            RoomNumMsg roomNumMsg = responseMessage.getData(RoomNumMsg.class);
            log.info("异步回调 : {}", roomNumMsg.roomCount);
        });
    }

    void invokeModuleMessageFuture() {
        CompletableFuture<ResponseMessage> future;

        try {
            // 路由
            future = flowContext.invokeModuleMessageFuture(cmdInfo);
            ResponseMessage responseMessage = future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        // 路由、请求参数
        future = flowContext.invokeModuleMessageFuture(cmdInfo, yourData);
    }

    /**
     * 通讯方式特点：请求同类型多个逻辑服通信结果
     * <pre>
     *     可接收多个游戏逻辑服的响应
     *     提供同步、异步回调、异步的编码风格
     *
     *     <a href="https://www.yuque.com/iohao/game/rf9rb9">文档</a>
     * </pre>
     */
    void invokeModuleCollectMessage() {
        // 同步
        this.invokeModuleCollectMessageSync();
        // 异步回调
        // --- 此回调写法，具备全链路调用日志跟踪 ---
        this.invokeModuleCollectMessageAsync();
        // 异步
        this.invokeModuleCollectMessageFuture();
    }

    void invokeModuleCollectMessageSync() {
        // 路由
        ResponseCollectMessage response = flowContext.invokeModuleCollectMessage(cmdInfo);

        for (ResponseCollectItemMessage message : response.getMessageList()) {
            RoomNumMsg roomNumMsg = message.getData(RoomNumMsg.class);
            log.info("同步调用 : {}", roomNumMsg.roomCount);
        }

        // 路由、请求参数
        ResponseCollectMessage response2 = flowContext.invokeModuleCollectMessage(cmdInfo, yourData);
        log.info("同步调用 : {}", response2.getMessageList());
    }

    void invokeModuleCollectMessageAsync() {
        // --- 此回调写法，具备全链路调用日志跟踪 ---

        // 路由、回调
        flowContext.invokeModuleCollectMessageAsync(cmdInfo, responseCollectMessage -> {
            List<ResponseCollectItemMessage> messageList = responseCollectMessage.getMessageList();

            for (ResponseCollectItemMessage message : messageList) {
                RoomNumMsg roomNumMsg = message.getData(RoomNumMsg.class);
                log.info("异步回调 : {}", roomNumMsg.roomCount);
            }
        });

        // 路由、请求参数、回调
        flowContext.invokeModuleCollectMessageAsync(cmdInfo, yourData, responseCollectMessage -> {
            log.info("异步回调 : {}", responseCollectMessage.getMessageList());
        });
    }

    void invokeModuleCollectMessageFuture() {
        CompletableFuture<ResponseCollectMessage> future;

        try {
            // 路由
            future = flowContext.invokeModuleCollectMessageFuture(cmdInfo);
            ResponseCollectMessage responseCollectMessage = future.get();
            log.info("异步 : {}", responseCollectMessage.getMessageList());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        // 路由、请求参数
        future = flowContext.invokeModuleCollectMessageFuture(cmdInfo, yourData);
    }

    /**
     * 通讯方式特点：获取玩家所在的游戏对外服的数据与扩展；换句话说就是，与玩家所在的游戏对外服通信。
     * <pre>
     *     只会访问玩家所在的【游戏对外服】（即使启动了多个游戏对外服）。
     *     提供同步、异步回调、异步的编码风格
     *
     *     <a href="https://www.yuque.com/iohao/game/ivxsw5">文档</a>
     * </pre>
     */
    void invokeExternalModuleCollectMessage() {
        // 同步
        this.invokeExternalModuleCollectMessageSync();
        // 异步回调
        // --- 此回调写法，具备全链路调用日志跟踪 ---
        this.invokeExternalModuleCollectMessageAsync();
        // 异步
        this.invokeExternalModuleCollectMessageFuture();
    }

    void invokeExternalModuleCollectMessageSync() {
        ResponseCollectExternalMessage response;

        // 业务码（类似路由）
        response = flowContext.invokeExternalModuleCollectMessage(bizCode);
        log.info("同步调用 : {}", response);

        // 业务码（类似路由）、请求参数
        response = flowContext.invokeExternalModuleCollectMessage(bizCode, yourData);
        log.info("同步调用 : {}", response);
    }

    void invokeExternalModuleCollectMessageAsync() {
        // 业务码（类似路由）、回调
        flowContext.invokeExternalModuleCollectMessageAsync(bizCode, response -> {
            response.optionalAnySuccess().ifPresent(itemMessage -> {
                Serializable data = itemMessage.getData();
                log.info("异步回调 {}", data);
            });
        });

        // 业务码（类似路由）、请求参数、回调
        flowContext.invokeExternalModuleCollectMessageAsync(bizCode, yourData, response -> {
            log.info("异步回调");
        });
    }

    void invokeExternalModuleCollectMessageFuture() {
        CompletableFuture<ResponseCollectExternalMessage> future;

        try {
            // 业务码（类似路由）
            future = flowContext.invokeExternalModuleCollectMessageFuture(bizCode);
            ResponseCollectExternalMessage response = future.get();
            log.info("异步");
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        // 业务码（类似路由）、请求参数
        future = flowContext.invokeExternalModuleCollectMessageFuture(bizCode, yourData);
        log.info("异步");
    }

    /**
     * 通讯方式特点：分布式事件总线
     * <pre>
     *     事件发布后，除了当前进程所有的订阅者能接收到，远程的订阅者也能接收到（跨机器、跨进程）
     *     可以代替 redis pub sub 、 MQ ，并且具备全链路调用日志跟踪，这点是中间件产品做不到的。
     *
     *     提供同步、异步的编码风格
     *
     *     <a href="https://www.yuque.com/iohao/game/gmxz33">文档</a>
     * </pre>
     */
    void eventBus() {
        int userId = 100;
        // 事件源
        YourEventMessage yourEventMessage = new YourEventMessage(userId);

        // 异步执行 - 事件发布后，只会交给 flowContext 关联的业务框架中的订阅者处理
        flowContext.fireMe(yourEventMessage);
        // 同步执行 - 事件发布后，只会交给 flowContext 关联的业务框架中的订阅者处理
        flowContext.fireMeSync(yourEventMessage);

        // 异步执行 - 事件发布后，当前进程所有的订阅者都会接收到。（同进程启动了多个逻辑服）
        flowContext.fireLocal(yourEventMessage);
        // 同步执行 - 事件发布后，当前进程所有的订阅者都会接收到。（同进程启动了多个逻辑服）
        flowContext.fireLocalSync(yourEventMessage);

        // 异步执行 - 除了当前进程所有的订阅者能接收到，远程的订阅者也能接收到（跨机器、跨进程）
        flowContext.fire(yourEventMessage);
        flowContext.fireSync(yourEventMessage);

        // 异步执行 - 除了当前进程所有的订阅者能接收到，远程的订阅者也能接收到（跨机器、跨进程）
        flowContext.fireAny(yourEventMessage);
        flowContext.fireAnySync(yourEventMessage);
    }

    @ActionMethod(1)
    public void broadcast(FlowContext flowContext) {
        // 全服广播 - 路由、业务数据
        flowContext.broadcast(cmdInfo, yourData);

        // 广播消息给单个用户 - 路由、业务数据、userId
        long userId = 100;
        flowContext.broadcast(cmdInfo, yourData, userId);

        // 广播消息给指定用户列表 - 路由、业务数据、userIdList
        List<Long> userIdList = new ArrayList<>();
        userIdList.add(100L);
        userIdList.add(200L);
        flowContext.broadcast(cmdInfo, yourData, userIdList);

        // 给自己发送消息 - 路由、业务数据
        flowContext.broadcastMe(cmdInfo, yourData);

        // 给自己发送消息 - 业务数据
        // 路由则使用当前 action 的路由。
        flowContext.broadcastMe(yourData);
    }

    @ActionMethod(2)
    public void broadcastOrder(FlowContext flowContext) {
        // 顺序 - 全服广播 - 路由、业务数据
        flowContext.broadcastOrder(cmdInfo, yourData);

        // 顺序 - 广播消息给单个用户 - 路由、业务数据、userId
        long userId = 100;
        flowContext.broadcastOrder(cmdInfo, yourData, userId);

        // 顺序 - 广播消息给指定用户列表 - 路由、业务数据、userIdList
        List<Long> userIdList = new ArrayList<>();
        userIdList.add(100L);
        userIdList.add(200L);
        flowContext.broadcastOrder(cmdInfo, yourData, userIdList);

        // 顺序 - 给自己发送消息 - 路由、业务数据
        flowContext.broadcastOrderMe(cmdInfo, yourData);

        // 顺序 - 给自己发送消息 - 业务数据
        // 路由则使用当前 action 的路由。
        flowContext.broadcastOrderMe(yourData);
    }

    void attachment(FlowContext flowContext) {
        /*
         * 不想扩展 FlowContext 子类时，推荐使用
         */

        // 获取元信息
        MyAttachment attachment = flowContext.getAttachment(MyAttachment.class);
        attachment.nickname = "渔民小镇";

        // [同步]更新 - 将元信息同步到玩家所在的游戏对外服中
        flowContext.updateAttachment(attachment);

        // [异步无阻塞]更新 - 将元信息同步到玩家所在的游戏对外服中
        flowContext.executeVirtual(() -> flowContext.updateAttachment(attachment));

        // [异步无阻塞]更新 - 将元信息同步到玩家所在的游戏对外服中
        flowContext.updateAttachmentAsync(attachment);
    }

    void attachment(MyFlowContext flowContext) {
        // 获取元信息
        MyAttachment attachment = flowContext.getAttachment();
        attachment.nickname = "渔民小镇";

        // [同步]更新 - 将元信息同步到玩家所在的游戏对外服中
        flowContext.updateAttachment();

        // [异步无阻塞]更新 - 将元信息同步到玩家所在的游戏对外服中
        flowContext.executeVirtual(flowContext::updateAttachment);

        // [异步无阻塞]更新 - 将元信息同步到玩家所在的游戏对外服中
        flowContext.updateAttachmentAsync();
    }

    static class MyFlowContext extends FlowContext {
        MyAttachment attachment;

        @Override
        @SuppressWarnings("unchecked")
        public MyAttachment getAttachment() {
            if (Objects.isNull(attachment)) {
                this.attachment = this.getAttachment(MyAttachment.class);
            }

            return this.attachment;
        }
    }

    static class MyAttachment implements Attachment {
        @Getter
        long userId;
        String nickname;
    }

    @Data
    static class YourEventMessage implements Serializable {
        final long userId;

        public YourEventMessage(long userId) {
            this.userId = userId;
        }
    }

    static class YourData implements Serializable {
    }

    void executor() {
        // 该方法具备全链路调用日志跟踪
        flowContext.execute(() -> {
            log.info("用户线程执行器");
        });

        flowContext.getExecutor().execute(() -> {
            log.info("用户线程执行器");
        });
    }

    void executeVirtual() {
        // 该方法具备全链路调用日志跟踪
        flowContext.executeVirtual(() -> {
            log.info("用户虚拟线程执行器");
        });

        flowContext.getVirtualExecutor().execute(() -> {
            log.info("用户虚拟线程执行器");
        });

        // 该方法具备全链路调用日志跟踪
        flowContext.executeVirtual(() -> {
            log.info("用户虚拟线程执行器");
            // 可以使用虚拟线程执行完成一些耗时的操作
            // 更新元信息
            flowContext.updateAttachment();
            // 其他业务逻辑
            // ... ...
        });
    }
}
