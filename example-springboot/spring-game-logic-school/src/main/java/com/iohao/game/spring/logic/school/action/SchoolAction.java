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
import com.iohao.game.action.skeleton.core.commumication.BroadcastContext;
import com.iohao.game.action.skeleton.core.exception.MsgException;
import com.iohao.game.bolt.broker.core.client.BrokerClientHelper;
import com.iohao.game.spring.common.cmd.SpringCmdModule;
import com.iohao.game.spring.common.pb.LogicRequestPb;
import com.iohao.game.spring.common.pb.SchoolLevelPb;
import com.iohao.game.spring.common.pb.SchoolPb;
import com.iohao.game.spring.common.pb.SpringBroadcastMessage;
import com.iohao.game.spring.logic.school.common.SpringGameCodeEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author 渔民小镇
 * @date 2022-07-09
 */
@Slf4j
@ActionController(SpringCmdModule.SchoolCmd.cmd)
public class SchoolAction {

    /**
     * 请求、响应
     *
     * @param logicRequestPb logicRequestPb
     * @return LogicRequestPb
     */
    @ActionMethod(SpringCmdModule.SchoolCmd.here)
    public LogicRequestPb here(LogicRequestPb logicRequestPb) {
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
     * 更新学校信息，jsr303
     *
     * @param schoolPb schoolPb
     */
    @ActionMethod(SpringCmdModule.SchoolCmd.jsr303)
    public void updateSchool(SchoolPb schoolPb) {
        /*
         * 进入业务方法需要满足这么几个条件
         * 1. SchoolPb.email 不能为 null ，并且是合法的电子邮件地址
         * 2. SchoolPb.classCapacity 学校最大教室容量不能超过 100 个
         * 3. SchoolPb.teacherNum 学校老师数量不能少于 60 个
         *
         * 相关文档 https://www.yuque.com/iohao/game/ghng6g
         */

        log.info("jsr303 : {}", schoolPb);
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
     * 触发广播
     */
    @ActionMethod(SpringCmdModule.SchoolCmd.broadcast)
    public void broadcast() {
        // 相关文档 https://www.yuque.com/iohao/game/qv4qfo

        // 广播上下文
        BroadcastContext broadcastContext = BrokerClientHelper.me().getBroadcastContext();
        // 业务数据
        SpringBroadcastMessage broadcastMessage = new SpringBroadcastMessage();

        // 广播消息的路由
        CmdInfo cmdInfo = CmdInfo.getCmdInfo(SpringCmdModule.SchoolCmd.cmd, SpringCmdModule.SchoolCmd.broadcastData);

        // 广播给指定玩家列表
        broadcastMessage.msg = "广播业务数据 - 1";
        broadcastContext.broadcast(cmdInfo, broadcastMessage, List.of(1L, 2L, 3L));

        // 全服广播
        broadcastMessage.msg = "广播业务数据 - 2";
        broadcastContext.broadcast(cmdInfo, broadcastMessage);
    }

    /*
     * JSR303
     * 断言 + 异常机制 = 清晰简洁的代码
     *
     * 请求、无响应
     * 请求、响应
     *
     * 广播指定玩家
     * 广播全服玩家
     *
     * 单个逻辑服与单个逻辑服通信请求 - 有返回值（可跨进程）
     * 单个逻辑服与单个逻辑服通信请求 - 无返回值（可跨进程）
     * 单个逻辑服与同类型多个逻辑服通信请求（可跨进程）
     *
     * 游戏文档生成
     * 业务.proto文件的生成
     */
}
