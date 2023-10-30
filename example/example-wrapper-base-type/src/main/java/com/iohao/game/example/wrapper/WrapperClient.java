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
package com.iohao.game.example.wrapper;

import com.iohao.game.action.skeleton.protocol.wrapper.*;
import com.iohao.game.common.kit.InternalKit;
import com.iohao.game.example.wrapper.action.WrapperCmd;
import com.iohao.game.external.client.AbstractInputCommandRegion;
import com.iohao.game.external.client.InputCommandRegion;
import com.iohao.game.external.client.join.ClientRunOne;
import com.iohao.game.external.client.kit.ClientUserConfigs;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author 渔民小镇
 * @date 2023-07-17
 */
@Slf4j
public class WrapperClient {
    public static void main(String[] args) {
        ClientUserConfigs.closeLog();

        // 模拟请求数据
        List<InputCommandRegion> inputCommandRegions = List.of(
                new InternalRegion()
        );

        // 启动模拟客户端
        new ClientRunOne()
                .setInputCommandRegions(inputCommandRegions)
                .startup();
    }

    static class InternalRegion extends AbstractInputCommandRegion {
        @Override
        public void initInputCommand() {
            inputCommandCreate.cmd = WrapperCmd.cmd;
            initStringCommand();
            initLongCommand();
            initIntCommand();
            initBooleanCommand();

            // 一秒后，执行模拟请求;
            InternalKit.newTimeoutSeconds(task -> {
                // 执行请求
                executeStringCommand();
                executeLongCommand();
                executeIntCommand();
                executeBooleanCommand();
            });
        }

        private void executeStringCommand() {
            ofRequestCommand(WrapperCmd.string2string).execute();
            ofRequestCommand(WrapperCmd.stringValue2stringValue).execute();
            ofRequestCommand(WrapperCmd.stringList2stringList).execute();
            ofRequestCommand(WrapperCmd.stringValueList2stringValueList).execute();
        }

        private void initStringCommand() {


            ofCommand(WrapperCmd.string2string).callback(result -> {
                String value = result.getString();
                log.info("value : {}", value);
            }).setTitle("string2string").setRequestData(() -> {
                StringValue stringValue = new StringValue();
                stringValue.value = "100";
                return stringValue;
            });

            ofCommand(WrapperCmd.stringValue2stringValue).callback(result -> {
                String value = result.getString();
                log.info("value : {}", value);
            }).setTitle("stringValue2stringValue").setRequestData(() -> {
                StringValue stringValue = new StringValue();
                stringValue.value = "100";
                return stringValue;
            });

            ofCommand(WrapperCmd.stringList2stringList).callback(result -> {
                        List<String> value = result.listString();
                        log.info("value : {}", value);
                    })
                    .setTitle("stringList2stringList")
                    .setRequestData(() -> StringValueList.of(List.of(1100L + "", 2200L + "")));

            ofCommand(WrapperCmd.stringValueList2stringValueList).callback(result -> {
                        List<String> value = result.listString();
                        log.info("value : {}", value);
                    })
                    .setTitle("stringValueList2stringValueList")
                    .setRequestData(() -> StringValueList.of(List.of(1100L + "", 2200L + "")));
        }

        private void executeLongCommand() {
            ofRequestCommand(WrapperCmd.long2long).execute();
            ofRequestCommand(WrapperCmd.longValue2longValue).execute();
            ofRequestCommand(WrapperCmd.longList2longList).execute();
            ofRequestCommand(WrapperCmd.longValueList2longValueList).execute();
        }

        private void initLongCommand() {
            ofCommand(WrapperCmd.long2long).callback(result -> {
                        var value = result.getLong();
                        log.info("value : {}", value);
                    })
                    .setTitle("long2long")
                    .setRequestData(() -> LongValue.of(100));

            ofCommand(WrapperCmd.longValue2longValue).callback(result -> {
                        var value = result.getLong();
                        log.info("value : {}", value);
                    })
                    .setTitle("longValue2longValue")
                    .setRequestData(() -> LongValue.of(100));


            ofCommand(WrapperCmd.longList2longList).callback(result -> {
                        List<Long> value = result.listLong();
                        log.info("value : {}", value);
                    })
                    .setTitle("longList2longList")
                    .setRequestData(() -> LongValueList.of(List.of(1100L, 2200L)));

            ofCommand(WrapperCmd.longValueList2longValueList).callback(result -> {
                        List<Long> value = result.listLong();
                        log.info("value : {}", value);
                    })
                    .setTitle("longValueList2longValueList")
                    .setRequestData(() -> LongValueList.of(List.of(1100L, 2200L)));
        }

        private void executeIntCommand() {
            ofRequestCommand(WrapperCmd.int2int).execute();
            ofRequestCommand(WrapperCmd.intValue2intValue).execute();
            ofRequestCommand(WrapperCmd.intList2intList).execute();
            ofRequestCommand(WrapperCmd.intValueList2intValueList).execute();
        }

        private void initIntCommand() {


            ofCommand(WrapperCmd.int2int).callback(result -> {
                var value = result.getInt();
                log.info("value : {}", value);
            }).setTitle("int2int").setRequestData(() -> {
                IntValue intValue = new IntValue();
                intValue.value = 100;
                return intValue;
            });

            ofCommand(WrapperCmd.intValue2intValue).callback(result -> {
                var value = result.getInt();
                log.info("value : {}", value);
            }).setTitle("intValue2intValue").setRequestData(() -> {
                IntValue intValue = new IntValue();
                intValue.value = 100;
                return intValue;
            });

            ofCommand(WrapperCmd.intList2intList).callback(result -> {
                        List<Integer> value = result.listInt();
                        log.info("value : {}", value);
                    })
                    .setTitle("intList2intList")
                    .setRequestData(() -> IntValueList.of(List.of(100, 200)));

            ofCommand(WrapperCmd.intValueList2intValueList).callback(result -> {
                        List<Integer> value = result.listInt();
                        log.info("value : {}", value);
                    })
                    .setTitle("intValueList2intValueList")
                    .setRequestData(() -> IntValueList.of(List.of(100, 200)));
        }

        private void executeBooleanCommand() {
            ofRequestCommand(WrapperCmd.bool2bool).execute();
            ofRequestCommand(WrapperCmd.boolValue2boolValue).execute();
            ofRequestCommand(WrapperCmd.boolList2boolList).execute();
            ofRequestCommand(WrapperCmd.boolValueList2boolValueList).execute();
        }

        private void initBooleanCommand() {

            ofCommand(WrapperCmd.bool2bool).callback(result -> {
                        var value = result.getBoolean();
                        log.info("value : {}", value);
                    })
                    .setTitle("bool2bool")
                    .setRequestData(() -> BoolValue.of(false));

            ofCommand(WrapperCmd.boolValue2boolValue).callback(result -> {
                        var value = result.getBoolean();

                        log.info("value : {}", value);
                    })
                    .setTitle("boolValue2boolValue")
                    .setRequestData(() -> BoolValue.of(false));


            ofCommand(WrapperCmd.boolList2boolList).callback(result -> {
                        List<Boolean> value = result.listBoolean();
                        log.info("value : {}", value);
                    })
                    .setTitle("boolList2boolList")
                    .setRequestData(() -> BoolValueList.of(List.of(false, true)));

            ofCommand(WrapperCmd.boolValueList2boolValueList).callback(result -> {
                        List<Boolean> value = result.listBoolean();
                        log.info("value : {}", value);
                    })
                    .setTitle("boolValueList2boolValueList")
                    .setRequestData(() -> BoolValueList.of(List.of(false, true)));
        }
    }
}
