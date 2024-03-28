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
package com.iohao.game.spring;

import com.iohao.game.action.skeleton.ext.spring.ActionFactoryBeanForSpring;
import com.iohao.game.bolt.broker.client.AbstractBrokerClientStartup;
import com.iohao.game.bolt.broker.core.client.BrokerClient;
import com.iohao.game.bolt.broker.core.client.BrokerClientBuilder;
import com.iohao.game.bolt.broker.server.BrokerServer;
import com.iohao.game.external.core.ExternalServer;
import com.iohao.game.external.core.config.ExternalJoinEnum;
import com.iohao.game.external.core.netty.simple.NettyRunOne;
import com.iohao.game.spring.broker.GameBrokerBoot;
import com.iohao.game.spring.external.GameExternal;
import com.iohao.game.spring.logic.classes.GameLogicClassesClient;
import com.iohao.game.spring.logic.hall.GameLogicHallClient;
import com.iohao.game.spring.logic.interaction.same.room.SameRoomLogicClient;
import com.iohao.game.spring.logic.school.GameLogicSchoolClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * 综合示例一键启动类
 * <p>
 * 文档： <a href="https://www.yuque.com/iohao/game/ruaqza">...</a>
 * <p>
 * 示例涉及如下知识点
 * <pre>
 * JSR380
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
 *
 * 登录：重复登录、顶号登录
 * </pre>
 *
 * @author 渔民小镇
 * @date 2022-07-09
 */
@Slf4j
@SpringBootApplication
public class SpringGameOneApplication {

    public static void main(String[] args) {
        // 游戏逻辑服列表
        List<AbstractBrokerClientStartup> logicList = List.of(
                // 大厅逻辑服 - 有登录
                new GameLogicHallClient()
                // 学校逻辑服
                , new GameLogicSchoolClient()
                // 班级逻辑服
                , new GameLogicClassesClient()
                /*
                 * 启动多个同类型的游戏逻辑服
                 *
                 * 启动 2 个房间的游戏逻辑服
                 * 方便测试 请求同类型多个逻辑服通信结果
                 * https://www.yuque.com/iohao/game/rf9rb9
                 */
                , createRoomLogicClient(1)
                , createRoomLogicClient(2)
        );

        // 启动 spring boot
        SpringApplication.run(SpringGameOneApplication.class, args);

        // 对外开放的端口
        int externalPort = 10100;
        // 游戏对外服
        ExternalServer externalServerWebSocket = new GameExternal()
                .createExternalServer(externalPort);

        // externalPort + 1
        int tcpPort = ExternalJoinEnum.TCP.cocPort(externalPort);
        ExternalServer externalServerTcp = new GameExternal()
                .createExternalServer(tcpPort, ExternalJoinEnum.TCP);

        // broker （游戏网关）
        BrokerServer brokerServer = new GameBrokerBoot().createBrokerServer();

        // 多服单进程的方式部署（类似单体应用）
        new NettyRunOne()
                // broker （游戏网关）
                .setBrokerServer(brokerServer)
                // 游戏对外服
                .setExternalServerList(List.of(externalServerWebSocket, externalServerTcp))
                // 游戏逻辑服列表
                .setLogicServerList(logicList)
                // 启动 游戏对外服、游戏网关、游戏逻辑服
                .startup();

        // spring 集成 https://www.yuque.com/iohao/game/evkgnz

        // 生成对接文档
        // BarSkeletonDoc.me().buildDoc();
    }

    @Bean
    public ActionFactoryBeanForSpring actionFactoryBean() {
        // 将业务框架交给 spring 管理
        return ActionFactoryBeanForSpring.me();
    }

    private static SameRoomLogicClient createRoomLogicClient(int id) {
        // BrokerClient 构建器，房间逻辑服的信息
        BrokerClientBuilder brokerClientBuilder = BrokerClient.newBuilder()
                // 逻辑服的唯一 id
                .id(String.valueOf(id))
                // 逻辑服名字
                .appName("spring 房间的游戏逻辑服-" + id)
                // 同类型标签
                .tag("roomLogic");

        // 创建房间的逻辑服
        SameRoomLogicClient sameRoomLogicClient = new SameRoomLogicClient();
        // 如果字段赋值了，就不会使用 BrokerClientStartup.createBrokerClientBuilder() 接口的值
        sameRoomLogicClient.setBrokerClientBuilder(brokerClientBuilder);
        return sameRoomLogicClient;
    }
}
