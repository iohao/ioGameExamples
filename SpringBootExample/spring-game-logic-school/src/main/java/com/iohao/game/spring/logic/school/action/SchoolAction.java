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
package com.iohao.game.spring.logic.school.action;

import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.annotation.ValidatedGroup;
import com.iohao.game.action.skeleton.core.CmdInfo;
import com.iohao.game.action.skeleton.core.DataCodecKit;
import com.iohao.game.action.skeleton.core.exception.MsgException;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.action.skeleton.core.flow.MyFlowContext;
import com.iohao.game.action.skeleton.protocol.ResponseMessage;
import com.iohao.game.action.skeleton.protocol.collect.ResponseCollectItemMessage;
import com.iohao.game.action.skeleton.protocol.collect.ResponseCollectMessage;
import com.iohao.game.spring.common.SpringGameCodeEnum;
import com.iohao.game.spring.common.Update;
import com.iohao.game.spring.common.cmd.ClassesCmdModule;
import com.iohao.game.spring.common.cmd.RoomCmdModule;
import com.iohao.game.spring.common.cmd.SchoolCmdModule;
import com.iohao.game.spring.common.pb.*;
import com.iohao.game.spring.logic.school.service.SchoolService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@AllArgsConstructor
@ActionController(SchoolCmdModule.cmd)
public class SchoolAction {

    SchoolService schoolService;

    @ActionMethod(SchoolCmdModule.here2)
    public LogicRequestPb here2(LogicRequestPb logicRequestPb) {
        schoolService.helloSpring();
        LogicRequestPb newLogicRequestPb = new LogicRequestPb();
        newLogicRequestPb.name = logicRequestPb.name + ", I'm here ";

        return newLogicRequestPb;
    }

    /**
     * 请求、响应
     *
     * @param logicRequestPb logicRequestPb
     * @return LogicRequestPb
     */
    @ActionMethod(SchoolCmdModule.here)
    public LogicRequestPb here(LogicRequestPb logicRequestPb, MyFlowContext flowContext) {

        schoolService.helloSpring();

        log.info("请求、响应 : {}", logicRequestPb);

        log.info("my flowContext : {}", flowContext.getClass());
        flowContext.hello();

        LogicRequestPb newLogicRequestPb = new LogicRequestPb();
        newLogicRequestPb.name = logicRequestPb.name + ", I'm here ";

        return newLogicRequestPb;
    }

    /**
     * 请求、无响应
     *
     * @param logicRequestPb logicRequestPb
     */
    @ActionMethod(SchoolCmdModule.hereVoid)
    public void hereVoid(LogicRequestPb logicRequestPb) {
        log.info("请求、无响应 : {}", logicRequestPb);
    }

    /**
     * 更新学校信息，jsr380
     *
     * @param schoolPb schoolPb
     */
    @ActionMethod(SchoolCmdModule.jsr380)
    public void updateSchool(SchoolPb schoolPb) {
        /*
         * 进入业务方法需要满足这么几个条件
         * 1. SchoolPb.email 不能为 null ，并且是合法的电子邮件地址
         * 2. SchoolPb.classCapacity 学校最大教室容量不能超过 100 个
         * 3. SchoolPb.teacherNum 学校老师数量不能少于 60 个
         *
         * 相关文档 https://iohao.github.io/game/docs/core/jsr380
         */

        log.info("jsr380 : {}", schoolPb);
    }

    /**
     * 更新学校信息，支持分组校验
     *
     * @param schoolPb schoolPb
     */
    @ActionMethod(SchoolCmdModule.group)
    public void updateSchoolGroup(@ValidatedGroup(value = Update.class) SchoolPb schoolPb) {
        /*
         * 进入业务方法需要满足这么几个条件
         * 1. SchoolPb.email 不能为 null ，并且是合法的电子邮件地址
         * 2. SchoolPb.classCapacity 学校最大教室容量不能超过 100 个
         * 3. SchoolPb.teacherNum 学校老师数量不能少于 60 个
         * 4. SchoolPb.name 在使用@Validated(value = Update.class) 注解的对象后会验证非null 否则不验证
         * 相关文档 https://iohao.github.io/game/docs/core/jsr380
         */

        log.info("支持分组校验 {}", schoolPb);
    }

    /**
     * 断言 + 异常机制 = 清晰简洁的代码
     *
     * @param schoolLevelPb schoolLevelPbe
     * @throws MsgException e
     */
    @ActionMethod(SchoolCmdModule.assertWithException)
    public void assertWithException(SchoolLevelPb schoolLevelPb) throws MsgException {
        // 断言必须是 true, 否则抛出异常
        SpringGameCodeEnum.levelMax.assertTrue(schoolLevelPb.level > 10);

        // 断言为 true, 就抛出异常
        SpringGameCodeEnum.vipLevelEnough.assertTrueThrows(schoolLevelPb.vipLevel <= 3);

        log.info("断言 + 异常机制 = 清晰简洁的代码 : {}", schoolLevelPb);

        // 相关文档 https://iohao.github.io/game/docs/manual/assert_game_code
    }

    /**
     * 2. 推送 触发广播
     */
    @ActionMethod(SchoolCmdModule.broadcast)
    public void broadcast(FlowContext flowContext) {
        /*
         * 相关文档
         * https://iohao.github.io/game/docs/communication/broadcast
         */

        // 业务数据
        SpringBroadcastMessagePb broadcastMessage = new SpringBroadcastMessagePb();

        // 广播消息的路由
        CmdInfo cmdInfo = CmdInfo.of(SchoolCmdModule.cmd, SchoolCmdModule.broadcastData);

        // 广播给指定玩家列表
        broadcastMessage.msg = "广播业务数据 - 1";

        List<Long> userIdList = new ArrayList<>();
        userIdList.add(1L);
        userIdList.add(2L);

        flowContext.broadcast(cmdInfo, broadcastMessage, userIdList);

        // 全服广播
        broadcastMessage.msg = "广播业务数据 - 2";
        flowContext.broadcast(cmdInfo, broadcastMessage);
    }

    /**
     * 3.1 单个逻辑服与单个逻辑服通信请求 - 有返回值（可跨进程）
     */
    @ActionMethod(SchoolCmdModule.communication31)
    public void communication31(FlowContext flowContext) {
        log.info("communication31 - 3.1 单个逻辑服与单个逻辑服通信请求 - 有返回值（可跨进程）");

        /*
         * 3.1 单个逻辑服与单个逻辑服通信请求 - 有返回值（可跨进程）
         * 相关文档 https://iohao.github.io/game/docs/manual/communication_model#request/response
         *
         * 这里演示的是无参的业务请求，invokeModuleMessage 方法是有业务参数重载的
         */

        CmdInfo cmdInfo = CmdInfo.of(ClassesCmdModule.cmd, ClassesCmdModule.getClasses);

        // 下述两个方法等价，分别演示了同步、异步回调

        // ------------ 异步回调 - 无阻塞 ------------
        flowContext.invokeModuleMessageAsync(cmdInfo, responseMessage -> {
            ClassesPb classesPb = responseMessage.getData(ClassesPb.class);
            log.info("异步回调 - 3.1 简化版本的请求 - 单个逻辑服与单个逻辑服通信请求 - 有返回值- classesPb : {}", classesPb);
        });

        // ------------ 同步调用 - 阻塞------------
        ResponseMessage responseMessage = flowContext.invokeModuleMessage(cmdInfo);
        // 表示没有错误
        if (responseMessage.getResponseStatus() == 0) {
            ClassesPb classesPb = responseMessage.getData(ClassesPb.class);
            log.info("同步调用 - 3.1 单个逻辑服与单个逻辑服通信请求 - 有返回值 classesPb : {}", classesPb);
        }
    }

    /**
     * 3.2 单个逻辑服与单个逻辑服通信请求 - 无返回值（可跨进程）
     */
    @ActionMethod(SchoolCmdModule.communication32)
    public void communication32(FlowContext flowContext) {
        log.info("communication32 - 3.2 单个逻辑服与单个逻辑服通信请求 - 无返回值（可跨进程）");
        /*
         * 3.2 单个逻辑服与单个逻辑服通信请求 - 无返回值（可跨进程）
         * 相关文档 https://iohao.github.io/game/docs/manual/communication_model#request/void
         *
         * 这里演示的是有参的业务请求，invokeModuleVoidMessage 方法还有无业务参数重载的
         */

        // 通信路由
        CmdInfo cmdInfo = CmdInfo.of(ClassesCmdModule.cmd, ClassesCmdModule.classesHereVoid);

        // 业务参数
        ClassesPb classesPb = new ClassesPb();
        classesPb.studentNum = 999;
        flowContext.invokeModuleVoidMessage(cmdInfo, classesPb);
    }

    /**
     * 3.3 单个逻辑服与同类型多个逻辑服通信请求（可跨进程）
     */
    @ActionMethod(SchoolCmdModule.communication33)
    public void communication33(FlowContext flowContext) {
        log.info("communication33 - 3.3 单个逻辑服与同类型多个逻辑服通信请求（可跨进程） - 统计房间");

        /*
         * 这个方法调用的 spring-game-logic-room-interaction-same （房间的游戏逻辑服）里的 action
         * 房间的游戏逻辑服是启动了多个的。
         *
         * 3.3 单个逻辑服与同类型多个逻辑服通信请求（可跨进程）
         * 相关文档 https://iohao.github.io/game/docs/manual/communication_model#request/multiple_response
         */

        // 路由：这个路由是将要访问逻辑服的路由（表示你将要去的地方）
        CmdInfo cmdInfo = CmdInfo.of(RoomCmdModule.cmd, RoomCmdModule.countRoom);

        // 下述两个方法等价，分别演示了同步、异步回调

        // ------------ 异步回调 - 无阻塞 ------------
        // 根据路由信息来请求其他【同类型】的多个子服务器（其他逻辑服）数据
        flowContext.invokeModuleCollectMessageAsync(cmdInfo, responseCollectMessage -> {
            // 每个逻辑服返回的数据集合
            List<ResponseCollectItemMessage> messageList = responseCollectMessage.getMessageList();
            log.info("messageList : {}", messageList);
        });

        // ------------ 同步调用 - 阻塞------------
        ResponseCollectMessage responseCollectMessage = flowContext.invokeModuleCollectMessage(cmdInfo);
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
    @ActionMethod(SchoolCmdModule.intValueWrapper)
    public int intValueWrapper(int level) {
        log.info("碎片协议 {}", level);
        /*
         * 相关文档 https://iohao.github.io/game/docs/manual/protocol_fragment
         */
        return level + 2;
    }
}
