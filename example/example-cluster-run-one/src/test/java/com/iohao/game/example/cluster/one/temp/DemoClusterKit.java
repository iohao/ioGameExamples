/*
 * ioGame
 * Copyright (C) 2021 - 2023  渔民小镇 （262610965@qq.com、luoyizhu@gmail.com） . All Rights Reserved.
 * # iohao.com . 渔民小镇
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package com.iohao.game.example.cluster.one.temp;

import io.scalecube.cluster.Cluster;
import io.scalecube.cluster.ClusterImpl;
import io.scalecube.cluster.ClusterMessageHandler;
import io.scalecube.cluster.Member;
import io.scalecube.cluster.membership.MembershipEvent;
import io.scalecube.cluster.transport.api.Message;
import io.scalecube.net.Address;
import io.scalecube.transport.netty.tcp.TcpTransportFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 渔民小镇
 * @date 2023-05-26
 */
@Slf4j
public class DemoClusterKit {
    static Cluster getCluster(String name, int gossipListenPort) {
        List<String> seedAddress = List.of(
                "127.0.0.1:30056",
                "127.0.0.1:30057",
                "127.0.0.1:30058"
        );

        var seedMemberAddress = seedAddress
                .stream()
                .map(Address::from)
                .toList();

        // Start seed member Alice
        Cluster alice = new ClusterImpl()
                .config(opts -> {
                    opts.memberAlias(name);
                    opts.externalPort(gossipListenPort);
                    return opts;
                })
                .membership(membershipConfig -> {
                    return membershipConfig
                            // 种子节点地址
                            .seedMembers(seedMemberAddress)
                            // 时间间隔
                            .syncInterval(5_000);
                })
                .handler(cluster -> new ClusterMessageHandler() {
                    @Override
                    public void onMessage(Message message) {
                        //
                        log.info("\n{}", name + " received: " + message.data());
                    }

                    @Override
                    public void onGossip(Message gossip) {
                        //
                        log.info("\n{}", name + " received: " + gossip.data());
                    }

                    @Override
                    public void onMembershipEvent(MembershipEvent event) {
                        //
                        log.info("\n{} {}", name + " received: " + event.member().alias(), event);
                        int size = cluster.members().size();
                        String mStr = cluster.members().stream()
                                .map(Member::toString)
                                .collect(Collectors.joining("\n"));

                        log.info("size : {} {} \n{}", size, event.type(), mStr);
                    }
                })
                .transportFactory(TcpTransportFactory::new)
                .transport(transportConfig -> transportConfig.port(gossipListenPort))
                .startAwait();

        return alice;
    }
}
