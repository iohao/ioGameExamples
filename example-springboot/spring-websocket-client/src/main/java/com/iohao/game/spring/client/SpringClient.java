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
package com.iohao.game.spring.client;

import com.iohao.game.action.skeleton.protocol.wrapper.*;
import com.iohao.game.common.kit.InternalKit;
import com.iohao.game.external.client.AbstractInputCommandRegion;
import com.iohao.game.external.client.InputCommandRegion;
import com.iohao.game.external.client.join.ClientRunOne;
import com.iohao.game.external.client.kit.ClientUserConfigs;
import com.iohao.game.external.client.kit.ScannerKit;
import com.iohao.game.external.core.config.ExternalGlobalConfig;
import com.iohao.game.external.core.config.ExternalJoinEnum;
import com.iohao.game.spring.common.cmd.*;
import com.iohao.game.spring.common.data.MyAttachment;
import com.iohao.game.spring.common.pb.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author 渔民小镇
 * @date 2023-07-17
 */
@Slf4j
public class SpringClient {
    public static void main(String[] args) {
        ClientUserConfigs.closeLog();

        // 模拟请求数据
        List<InputCommandRegion> inputCommandRegions = List.of(
                new InternalHallRegion()
                , new InternalClassesRegion()
                , new InternalSchoolRegion()
                , new InternalRoomRegion()
                , new InternalOtherSchoolRegion()
        );

        // 使用 tcp 连接服务器
        ExternalJoinEnum joinEnum = ExternalJoinEnum.TCP;
        // 使用 webSocket 连接服务器
        joinEnum = ExternalJoinEnum.WEBSOCKET;

        // 启动模拟客户端
        new ClientRunOne()
                .setJoinEnum(joinEnum)
                .setConnectPort(joinEnum.cocPort(ExternalGlobalConfig.externalPort))
                .setInputCommandRegions(inputCommandRegions)
                .startup();
    }

    static class InternalHallRegion extends AbstractInputCommandRegion {
        @Override
        public void initInputCommand() {
            inputCommandCreate.cmd = HallCmdModule.cmd;

            initCommandLogin();
            initCommandAttachment();

            extractedList();

            // 延迟执行模拟请求;
            InternalKit.newTimeout(timeout -> {
                // 执行请求
                ofRequestCommand(HallCmdModule.loginVerify).execute();

            }, 100, TimeUnit.MILLISECONDS);
        }

        private void extractedList() {
            ofCommand(HallCmdModule.acceptList).setTitle("接收与请求 List<Animal> 示例").setRequestData(() -> {
                // 请求参数
                Animal animal_1 = new Animal();
                animal_1.id = 1;
                animal_1.animalType = AnimalType.BIRD;

                Animal animal_2 = new Animal();
                animal_2.id = 2;
                animal_2.animalType = AnimalType.CAT;

                List<Animal> animalList = List.of(animal_1, animal_2);

                return WrapperKit.ofListByteValue(animalList);
            }).callback(result -> {
                List<Animal> list = result.listValue(Animal.class);
                log.info("接收与请求 List<Animal> 示例 : {}", list);
            });
        }

        private void initCommandLogin() {
            /*
             *       注意，这个业务码放这里只是为了方便测试多种情况
             *       交由测试请求端来控制
             *
             *       ------------业务码说明------------------
             *       当业务码为 【0】时 (相当于号已经在线上了，不能重复登录)
             *       如果游戏对外服已经有该玩家的连接了，就抛异常，告诉请求端玩家已经在线。
             *       否则就正常登录成功
             *
             *       当业务码为 【1】时 （相当于顶号）
             *       强制断开之前的客户端连接，并让本次登录成功。
             */
            int loginBizCode = 1;

            // 登录请求
            ofCommand(HallCmdModule.loginVerify).setTitle("登录").setRequestData(() -> {
                LoginVerify loginVerify = new LoginVerify();
                loginVerify.age = 273676;
                loginVerify.jwt = "luoyi";
                loginVerify.loginBizCode = loginBizCode;
                return loginVerify;
            }).callback(result -> {
                UserInfo value = result.getValue(UserInfo.class);
                log.info("登录成功 : {}", value);
            });
        }

        private void initCommandAttachment() {
            ofCommand(HallCmdModule.attachment).setTitle("设置元信息");

            ofCommand(HallCmdModule.attachmentPrint).setTitle("打印元信息").callback(result -> {
                MyAttachment value = result.getValue(MyAttachment.class);
                log.info("value : {}", value);
            });
        }
    }

    static class InternalClassesRegion extends AbstractInputCommandRegion {
        @Override
        public void initInputCommand() {
            inputCommandCreate.cmd = ClassesCmdModule.cmd;

            ofCommand(ClassesCmdModule.issu143).setTitle("issu143").callback(result -> {
                var value = result.getString();
                log.info("value : {}", value);
            });
        }
    }

    static class InternalSchoolRegion extends AbstractInputCommandRegion {
        @Override
        public void initInputCommand() {
            inputCommandCreate.cmd = SchoolCmdModule.cmd;

            initCommand();
            initCommandCommunication();
            listen();

        }

        private void initCommand() {


            ofCommand(SchoolCmdModule.here).setTitle("here").setRequestData(() -> {
                LogicRequestPb logicRequestPb = new LogicRequestPb();
                logicRequestPb.name = "塔姆";
                return logicRequestPb;
            }).callback(result -> {
                LogicRequestPb value = result.getValue(LogicRequestPb.class);
                log.info("value : {}", value);
            });

            ofCommand(SchoolCmdModule.hereVoid).setTitle("hereVoid").setRequestData(() -> {
                LogicRequestPb logicRequestPb = new LogicRequestPb();
                logicRequestPb.name = "塔姆";
                return logicRequestPb;
            });


            ofCommand(SchoolCmdModule.jsr380).setTitle("jsr380 更新学校信息").setRequestData(() -> {
                ScannerKit.log(() -> log.info("请输入老师数量，少于 60 个将触发 jsr380 "));

                // 更新学校信息，jsr380
                SchoolPb schoolPb = new SchoolPb();
                schoolPb.email = "ioGame@game.com";
                schoolPb.classCapacity = 99;
                schoolPb.teacherNum = ScannerKit.nextInt(70);

                return schoolPb;
            });

            ofCommand(SchoolCmdModule.group).setTitle("分组校验").setRequestData(() -> {
                ScannerKit.log(() -> log.info("name=null 在加入分组校验时 该用例会返回校验失败: name不能为null"));

                // 更新学校信息，jsr380
                SchoolPb schoolPb = new SchoolPb();
                schoolPb.email = "ioGame@game.com";
                schoolPb.classCapacity = 99;
                schoolPb.teacherNum = 70;
                schoolPb.name = ScannerKit.nextLine("");

                if (Objects.equals("null", schoolPb.name)) {
                    schoolPb.name = null;
                }

                return schoolPb;
            });

            ofCommand(SchoolCmdModule.assertWithException).setTitle("断言 + 异常机制 = 清晰简洁的代码").setRequestData(() -> {
                ScannerKit.log(() -> log.info("请输入 level，小于 10 将触发异常"));

                // 断言 + 异常机制 = 清晰简洁的代码
                SchoolLevelPb schoolLevelPb = new SchoolLevelPb();
                schoolLevelPb.level = ScannerKit.nextInt(11);
                schoolLevelPb.vipLevel = 10;

                return schoolLevelPb;
            })
            ;

            ofCommand(SchoolCmdModule.broadcast).setTitle("触发广播");

            ofCommand(SchoolCmdModule.intValueWrapper)
                    .setTitle("业务参数自动装箱、拆箱基础类型")
                    .setRequestData(() -> IntValue.of(10))
                    .callback(result -> {
                        int value = result.getInt();
                        log.info("value : {}", value);
                    });
        }

        private void initCommandCommunication() {
            ofCommand(SchoolCmdModule.communication31)
                    .setTitle("3.1 单个逻辑服与单个逻辑服通信请求 - 有返回值（可跨进程）");

            ofCommand(SchoolCmdModule.communication32)
                    .setTitle("3.2 单个逻辑服与单个逻辑服通信请求 - 无返回值（可跨进程）");

            ofCommand(SchoolCmdModule.communication33)
                    .setTitle("3.3 单个逻辑服与同类型多个逻辑服通信请求（可跨进程） - 统计房间");
        }

        private void listen() {
            ofListen(result -> {
                SpringBroadcastMessagePb value = result.getValue(SpringBroadcastMessagePb.class);
                log.info("value : {}", value);
            }, SchoolCmdModule.broadcastData, "广播业务数据");
        }
    }

    static class InternalRoomRegion extends AbstractInputCommandRegion {
        @Override
        public void initInputCommand() {
            inputCommandCreate.cmd = RoomCmdModule.cmd;
            inputCommandCreate.cmdName = "房间";

            ofCommand(RoomCmdModule.helloRoom).setTitle("helloRoom").callback(result -> {
                OtherVerify value = result.getValue(OtherVerify.class);
                log.info("value : {}", value);
            });

            ofCommand(RoomCmdModule.countRoom).setTitle("countRoom").callback(result -> {
                RoomNumPb value = result.getValue(RoomNumPb.class);
                log.info("value : {}", value);
            });
        }
    }

    static class InternalOtherSchoolRegion extends AbstractInputCommandRegion {
        @Override
        public void initInputCommand() {
            inputCommandCreate.cmd = OtherSchoolCmdModule.cmd;

            ofCommand(OtherSchoolCmdModule.longValueWithBroadcast).setTitle("longValueWithBroadcast").callback(result -> {
                UserInfo value = result.getValue(UserInfo.class);
                log.info("value : {}", value);
            });

            ofListen(result -> {
                SchoolPb value = result.getValue(SchoolPb.class);
                log.info("value : {}", value);
            }, OtherSchoolCmdModule.longValueWithBroadcastData, "longValueWithBroadcastData");

            ofCommand(OtherSchoolCmdModule.longValueWrapper)
                    .setTitle("IgnoreDebugInout longValueWrapper")
                    .setRequestData(() -> LongValue.of(1))
                    .callback(result -> {
                        long value = result.getLong();
                        log.info("value : {}", value);
                    })
            ;
        }
    }
}
