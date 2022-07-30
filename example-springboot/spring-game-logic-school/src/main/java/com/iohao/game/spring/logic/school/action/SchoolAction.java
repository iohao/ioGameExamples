/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2022 double joker （262610965@qq.com） . All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.iohao.game.spring.logic.school.action;

import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.core.CmdInfo;
import com.iohao.game.action.skeleton.core.DataCodecKit;
import com.iohao.game.action.skeleton.core.commumication.BroadcastContext;
import com.iohao.game.action.skeleton.core.commumication.InvokeModuleContext;
import com.iohao.game.action.skeleton.core.exception.MsgException;
import com.iohao.game.action.skeleton.protocol.ResponseMessage;
import com.iohao.game.action.skeleton.protocol.collect.ResponseCollectItemMessage;
import com.iohao.game.action.skeleton.protocol.collect.ResponseCollectMessage;
import com.iohao.game.bolt.broker.core.client.BrokerClientHelper;
import com.iohao.game.spring.common.SpringCmdModule;
import com.iohao.game.spring.common.SpringGameCodeEnum;
import com.iohao.game.spring.common.pb.*;
import com.iohao.game.spring.logic.school.service.SchoolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 学校相关 action
 *
 * @author 渔民小镇
 * @date 2022-07-09
 */
@Slf4j
@Component
@ActionController(SpringCmdModule.SchoolCmd.cmd)
public class SchoolAction {

    @Autowired
    SchoolService schoolService;

    /**
     * 请求、响应
     *
     * @param logicRequestPb logicRequestPb
     * @return LogicRequestPb
     */
    @ActionMethod(SpringCmdModule.SchoolCmd.here)
    public LogicRequestPb here(LogicRequestPb logicRequestPb) {

        schoolService.helloSpring();

        // 相关文档 https://www.yuque.com/iohao/game/nelwuz#UAUE4

        log.info("请求、响应 : {}", logicRequestPb);

        LogicRequestPb newLogicRequestPb = new LogicRequestPb();
        newLogicRequestPb.name = logicRequestPb.name + ", I'm here ";

        return newLogicRequestPb;
    }

    /**
     * 请求、无响应
     *
     * @param logicRequestPb logicRequestPb
     */
    @ActionMethod(SpringCmdModule.SchoolCmd.hereVoid)
    public void hereVoid(LogicRequestPb logicRequestPb) {
        // 相关文档 https://www.yuque.com/iohao/game/nelwuz#qs7yJ

        log.info("请求、无响应 : {}", logicRequestPb);
    }

    /**
     * 更新学校信息，jsr380
     *
     * @param schoolPb schoolPb
     */
    @ActionMethod(SpringCmdModule.SchoolCmd.jsr380)
    public void updateSchool(SchoolPb schoolPb) {
        /*
         * 进入业务方法需要满足这么几个条件
         * 1. SchoolPb.email 不能为 null ，并且是合法的电子邮件地址
         * 2. SchoolPb.classCapacity 学校最大教室容量不能超过 100 个
         * 3. SchoolPb.teacherNum 学校老师数量不能少于 60 个
         *
         * 相关文档 https://www.yuque.com/iohao/game/ghng6g
         */

        log.info("jsr380 : {}", schoolPb);
    }

    /**
     * 断言 + 异常机制 = 清晰简洁的代码
     *
     * @param schoolLevelPb schoolLevelPbe
     * @throws MsgException e
     */
    @ActionMethod(SpringCmdModule.SchoolCmd.assertWithException)
    public void assertWithException(SchoolLevelPb schoolLevelPb) throws MsgException {
        // 断言必须是 true, 否则抛出异常
        SpringGameCodeEnum.levelMax.assertTrue(schoolLevelPb.level > 10);

        // 断言为 true, 就抛出异常
        SpringGameCodeEnum.vipLevelEnough.assertTrueThrows(schoolLevelPb.vipLevel <= 3);

        log.info("断言 + 异常机制 = 清晰简洁的代码 : {}", schoolLevelPb);

        // 相关文档 https://www.yuque.com/iohao/game/avlo99
    }

    /**
     * 2. 推送 触发广播
     */
    @ActionMethod(SpringCmdModule.SchoolCmd.broadcast)
    public void broadcast() {
        /*
         * 相关文档
         * https://www.yuque.com/iohao/game/nelwuz#vqvGQ
         * https://www.yuque.com/iohao/game/qv4qfo
         */

        // 广播上下文
        BroadcastContext broadcastContext = BrokerClientHelper.me().getBroadcastContext();
        // 业务数据
        SpringBroadcastMessagePb broadcastMessage = new SpringBroadcastMessagePb();

        // 广播消息的路由
        CmdInfo cmdInfo = CmdInfo.getCmdInfo(SpringCmdModule.SchoolCmd.cmd, SpringCmdModule.SchoolCmd.broadcastData);

        // 广播给指定玩家列表
        broadcastMessage.msg = "广播业务数据 - 1";

        List<Long> userIdList = new ArrayList<>();
        userIdList.add(1L);
        userIdList.add(2L);

        broadcastContext.broadcast(cmdInfo, broadcastMessage, userIdList);

        // 全服广播
        broadcastMessage.msg = "广播业务数据 - 2";
        broadcastContext.broadcast(cmdInfo, broadcastMessage);
    }

    /**
     * 3.1 单个逻辑服与单个逻辑服通信请求 - 有返回值（可跨进程）
     */
    @ActionMethod(SpringCmdModule.SchoolCmd.communication31)
    public void communication31() {
        log.info("communication31 - 3.1 单个逻辑服与单个逻辑服通信请求 - 有返回值（可跨进程）");

        /*
         * 3.1 单个逻辑服与单个逻辑服通信请求 - 有返回值（可跨进程）
         * 相关文档 https://www.yuque.com/iohao/game/nelwuz#L9TAJ
         *
         * 这里演示的是无参的业务请求，invokeModuleMessage 方法是有业务参数重载的
         */

        // 通信路由
        CmdInfo cmdInfo = CmdInfo.getCmdInfo(SpringCmdModule.ClassesCmd.cmd, SpringCmdModule.ClassesCmd.getClasses);
        // 内部模块通讯上下文，内部模块指的是游戏逻辑服
        InvokeModuleContext invokeModuleContext = BrokerClientHelper.me().getInvokeModuleContext();

        /*
         * 第一种使用方式
         */
        ResponseMessage responseMessage = invokeModuleContext.invokeModuleMessage(cmdInfo);
        // 表示没有错误
        if (responseMessage.getResponseStatus() == 0) {
            // 将字节解析成对象
            byte[] dataContent = responseMessage.getData();
            ClassesPb classesPb = DataCodecKit.decode(dataContent, ClassesPb.class);
            log.info("3.1 单个逻辑服与单个逻辑服通信请求 - 有返回值 classesPb : {}", classesPb);
        }

        // ------------------ 分割线 ------------------

        /*
         * 第二种使用方式
         * 当然，如果有对业务方法有自信确定不会有错误的，可以大胆的使用简化版本的请求
         * 这个请求得到的结果与上面是等价的
         */
        ClassesPb classesPb = invokeModuleContext.invokeModuleMessageData(cmdInfo, ClassesPb.class);
        log.info("3.1 简化版本的请求 - 单个逻辑服与单个逻辑服通信请求 - 有返回值- classesPb : {}", classesPb);

    }

    /**
     * 3.2 单个逻辑服与单个逻辑服通信请求 - 无返回值（可跨进程）
     */
    @ActionMethod(SpringCmdModule.SchoolCmd.communication32)
    public void communication32() {
        log.info("communication32 - 3.2 单个逻辑服与单个逻辑服通信请求 - 无返回值（可跨进程）");
        /*
         * 3.2 单个逻辑服与单个逻辑服通信请求 - 无返回值（可跨进程）
         * 相关文档 https://www.yuque.com/iohao/game/nelwuz#gtdrv
         *
         * 这里演示的是有参的业务请求，invokeModuleVoidMessage 方法还有无业务参数重载的
         */

        // 通信路由
        CmdInfo cmdInfo = CmdInfo.getCmdInfo(SpringCmdModule.ClassesCmd.cmd, SpringCmdModule.ClassesCmd.classesHereVoid);
        // 内部模块通讯上下文，内部模块指的是游戏逻辑服
        InvokeModuleContext invokeModuleContext = BrokerClientHelper.me().getInvokeModuleContext();

        // 业务参数
        ClassesPb classesPb = new ClassesPb();
        classesPb.studentNum = 999;

        invokeModuleContext.invokeModuleVoidMessage(cmdInfo, classesPb);
    }

    /**
     * 3.3 单个逻辑服与同类型多个逻辑服通信请求（可跨进程）
     */
    @ActionMethod(SpringCmdModule.SchoolCmd.communication33)
    public void communication33() {
        log.info("communication33 - 3.3 单个逻辑服与同类型多个逻辑服通信请求（可跨进程） - 统计房间");

        /*
         * 这个方法调用的 spring-game-logic-room-interaction-same （房间的游戏逻辑服）里的 action
         * 房间的游戏逻辑服是启动了多个的。
         *
         * 3.3 单个逻辑服与同类型多个逻辑服通信请求（可跨进程）
         * 相关文档 https://www.yuque.com/iohao/game/nelwuz#gSdya
         */

        // 路由：这个路由是将要访问逻辑服的路由（表示你将要去的地方）
        CmdInfo cmdInfo = CmdInfo.getCmdInfo(SpringCmdModule.RoomCmd.cmd, SpringCmdModule.RoomCmd.countRoom);
        InvokeModuleContext invokeModuleContext = BrokerClientHelper.me().getInvokeModuleContext();
        // 根据路由信息来请求其他【同类型】的多个子服务器（其他逻辑服）数据
        ResponseCollectMessage responseCollectMessage = invokeModuleContext.invokeModuleCollectMessage(cmdInfo);
        // 每个逻辑服返回的数据集合
        List<ResponseCollectItemMessage> messageList = responseCollectMessage.getMessageList();

        for (ResponseCollectItemMessage responseCollectItemMessage : messageList) {
            ResponseMessage responseMessage = responseCollectItemMessage.getResponseMessage();
            // 得到房间的逻辑服返回的业务数据
            RoomNumPb decode = DataCodecKit.decode(responseMessage.getData(), RoomNumPb.class);
            log.info("3.3 responseCollectItemMessage : {} ", decode);
        }

    }

    /**
     * 业务参数自动装箱、拆箱基础类型，解决碎片协议问题
     *
     * @param level 等级
     * @return level
     */
    @ActionMethod(SpringCmdModule.SchoolCmd.intPbWrapper)
    public int intPbWrapper(int level) {
        log.info("碎片协议 {}", level);
        /*
         * 相关文档 https://www.yuque.com/iohao/game/ieimzn
         */
        return level + 2;
    }
}
