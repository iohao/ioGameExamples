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
package com.iohao.game.example.external.cache;

import com.iohao.game.action.skeleton.core.doc.BarSkeletonDoc;
import com.iohao.game.bolt.broker.core.client.BrokerAddress;
import com.iohao.game.bolt.broker.core.common.IoGameGlobalConfig;
import com.iohao.game.example.common.cmd.CacheCmd;
import com.iohao.game.external.core.ExternalServer;
import com.iohao.game.external.core.config.ExternalGlobalConfig;
import com.iohao.game.external.core.config.ExternalJoinEnum;
import com.iohao.game.external.core.hook.cache.CmdCacheOption;
import com.iohao.game.external.core.hook.cache.internal.DefaultExternalCmdCache;
import com.iohao.game.external.core.netty.DefaultExternalServer;
import com.iohao.game.external.core.netty.DefaultExternalServerBuilder;
import com.iohao.game.external.core.netty.simple.NettyRunOne;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.List;

/**
 * @author 渔民小镇
 * @date 2024-02-02
 */
@Slf4j
public class CacheApplication {
    final int externalCorePort;
    final BrokerAddress brokerAddress = new BrokerAddress("127.0.0.1", IoGameGlobalConfig.brokerPort);

    public CacheApplication(int externalCorePort) {
        this.externalCorePort = externalCorePort;
    }

    public static void main(String[] args) {

        // 游戏对外服的缓存配置，文档 https://www.yuque.com/iohao/game/khg23pvbh59a7spm
        extractedExternalCache();

        int externalCorePort = ExternalGlobalConfig.externalPort;
        var application = new CacheApplication(externalCorePort);
        // 游戏对外服列表（提供 WebSocket、TCP 两种连接方式）
        List<ExternalServer> externalServerList = application.listExternalServer();

        new NettyRunOne()
                // 游戏对外服 list
                .setExternalServerList(externalServerList)
                // 游戏逻辑服 list
                .setLogicServerList(List.of(new CacheLogicServer()))
                .startup();

        // 生成对接文档
        BarSkeletonDoc.me().buildDoc();
    }

    List<ExternalServer> listExternalServer() {
        return List.of(
                // 连接方式；WEBSOCKET
                createExternalServer(ExternalJoinEnum.WEBSOCKET)
                // 连接方式；TCP
                , createExternalServer(ExternalJoinEnum.TCP)
        );
    }

    ExternalServer createExternalServer(ExternalJoinEnum join) {
        int port = externalCorePort;
        port = join.cocPort(port);
        DefaultExternalServerBuilder builder = DefaultExternalServer
                .newBuilder(port)
                // 连接方式
                .externalJoinEnum(join)
                // 与 Broker （游戏网关）的连接地址
                .brokerAddress(brokerAddress);

        return builder.build();
    }

    private static void extractedExternalCache() {
        // 框架内置的缓存实现类
        DefaultExternalCmdCache externalCmdCache = new DefaultExternalCmdCache();
        // 添加全局配置中
        ExternalGlobalConfig.externalCmdCache = externalCmdCache;

        // 即使不设置，框架默认也是这个配置，这里只是展示如何设置默认的缓存配置。
        CmdCacheOption defaultOption = CmdCacheOption.newBuilder()
                // 缓存过期时间，1 小时
                .setExpireTime(Duration.ofHours(1))
                // 缓存过期检测时间间隔 5 分钟
                .setExpireCheckTime(Duration.ofMinutes(5))
                // 同一个 action 的缓存数量上限设置为 256 条
                .setCacheLimit(256)
                // 构建缓存配置
                .build();

        // 设置为默认的缓存配置，之后添加的路由缓存都将使用这个缓存配置
        externalCmdCache.setCmdCacheOption(defaultOption);

        // 添加路由缓存 22-1，使用默认的缓存配置
        externalCmdCache.addCmd(CacheCmd.cmd, CacheCmd.cacheHere);

        // 新增一个缓存配置对象，对业务做更精细的控制。
        CmdCacheOption optionCustom = CmdCacheOption.newBuilder()
                // 缓存过期时间 30 秒
                .setExpireTime(Duration.ofSeconds(30))
                // 缓存过期检测时间间隔 5 秒
                .setExpireCheckTime(Duration.ofSeconds(5))
                // 构建缓存配置
                .build();

        // 添加路由缓存，使用自定义缓存配置
        externalCmdCache.addCmd(CacheCmd.cmd, CacheCmd.cacheCustom, optionCustom);
        externalCmdCache.addCmd(CacheCmd.cmd, CacheCmd.cacheList, optionCustom);
    }
}
