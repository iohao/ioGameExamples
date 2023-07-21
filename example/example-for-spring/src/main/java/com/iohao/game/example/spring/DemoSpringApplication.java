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
package com.iohao.game.example.spring;

import com.iohao.game.action.skeleton.ext.spring.ActionFactoryBeanForSpring;
import com.iohao.game.example.spring.server.DemoSpringLogicServer;
import com.iohao.game.external.core.netty.simple.NettySimpleHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * spring application 启动类
 * <pre>
 *     启动 对外服、网关服、逻辑服; 并生成游戏业务文档
 * </pre>
 *
 * @author 渔民小镇
 * @date 2022-03-22
 */
@SpringBootApplication
public class DemoSpringApplication {
    public static void main(String[] args) {
        // 启动 spring boot
        SpringApplication.run(DemoSpringApplication.class, args);

        // 游戏对外服端口
        int port = 10100;

        // spring 逻辑服
        var demoLogicServer = new DemoSpringLogicServer();

        // 启动 对外服、网关服、逻辑服; 并生成游戏业务文档
        NettySimpleHelper.run(port, List.of(demoLogicServer));

        /*
         * 该示例文档地址
         * https://www.yuque.com/iohao/game/evkgnz
         */
    }

    @Bean
    public ActionFactoryBeanForSpring actionFactoryBean() {
        // 将业务框架交给 spring 管理
        return ActionFactoryBeanForSpring.me();
    }
}
