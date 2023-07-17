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
            ofRequestCommand(WrapperCmd.string2string).request();
            ofRequestCommand(WrapperCmd.stringValue2stringValue).request();
            ofRequestCommand(WrapperCmd.stringList2stringList).request();
            ofRequestCommand(WrapperCmd.stringValueList2stringValueList).request();
        }

        private void initStringCommand() {
            StringValue stringValue = new StringValue();
            stringValue.value = "100";

            ofCommand(WrapperCmd.string2string).callback(StringValue.class, result -> {
                StringValue value = result.getValue();
                log.info("value : {}", value);
            }).setDescription("string2string").setRequestData(stringValue);

            ofCommand(WrapperCmd.stringValue2stringValue).callback(StringValue.class, result -> {
                StringValue value = result.getValue();
                log.info("value : {}", value);
            }).setDescription("stringValue2stringValue").setRequestData(stringValue);


            StringValueList stringValueList = StringValueList.of(List.of(1100L + "", 2200L + ""));
            ofCommand(WrapperCmd.stringList2stringList).callback(StringValueList.class, result -> {
                List<String> value = result.toList(String.class);
                log.info("value : {}", value);
            }).setDescription("stringList2stringList").setRequestData(stringValueList);

            ofCommand(WrapperCmd.stringValueList2stringValueList).callback(StringValueList.class, result -> {
                List<String> value = result.toList(String.class);
                log.info("value : {}", value);
            }).setDescription("stringValueList2stringValueList").setRequestData(stringValueList);
        }

        private void executeLongCommand() {
            ofRequestCommand(WrapperCmd.long2long).request();
            ofRequestCommand(WrapperCmd.longValue2longValue).request();
            ofRequestCommand(WrapperCmd.longList2longList).request();
            ofRequestCommand(WrapperCmd.longValueList2longValueList).request();
        }

        private void initLongCommand() {
            LongValue longValue = new LongValue();
            longValue.value = 100;

            ofCommand(WrapperCmd.long2long).callback(LongValue.class, result -> {
                LongValue value = result.getValue();
                log.info("value : {}", value);
            }).setDescription("long2long").setRequestData(longValue);

            ofCommand(WrapperCmd.longValue2longValue).callback(LongValue.class, result -> {
                LongValue value = result.getValue();
                log.info("value : {}", value);
            }).setDescription("longValue2longValue").setRequestData(longValue);


            LongValueList longValueList = LongValueList.of(List.of(1100L, 2200L));
            ofCommand(WrapperCmd.longList2longList).callback(LongValueList.class, result -> {
                List<Long> value = result.toList(Long.class);
                log.info("value : {}", value);
            }).setDescription("longList2longList").setRequestData(longValueList);

            ofCommand(WrapperCmd.longValueList2longValueList).callback(LongValueList.class, result -> {
                List<Long> value = result.toList(Long.class);
                log.info("value : {}", value);
            }).setDescription("longValueList2longValueList").setRequestData(longValueList);
        }

        private void executeIntCommand() {
            ofRequestCommand(WrapperCmd.int2int).request();
            ofRequestCommand(WrapperCmd.intValue2intValue).request();
            ofRequestCommand(WrapperCmd.intList2intList).request();
            ofRequestCommand(WrapperCmd.intValueList2intValueList).request();
        }

        private void initIntCommand() {
            IntValue intValue = new IntValue();
            intValue.value = 100;

            ofCommand(WrapperCmd.int2int).callback(IntValue.class, result -> {
                IntValue value = result.getValue();
                log.info("value : {}", value);
            }).setDescription("int2int").setRequestData(intValue);

            ofCommand(WrapperCmd.intValue2intValue).callback(IntValue.class, result -> {
                IntValue value = result.getValue();
                log.info("value : {}", value);
            }).setDescription("intValue2intValue").setRequestData(intValue);


            IntValueList intValueList = IntValueList.of(List.of(100, 200));
            ofCommand(WrapperCmd.intList2intList).callback(LongValueList.class, result -> {
                List<Integer> value = result.toList(Integer.class);
                log.info("value : {}", value);
            }).setDescription("intList2intList").setRequestData(intValueList);

            ofCommand(WrapperCmd.intValueList2intValueList).callback(LongValueList.class, result -> {
                List<Integer> value = result.toList(Integer.class);
                log.info("value : {}", value);
            }).setDescription("intValueList2intValueList").setRequestData(intValueList);
        }

        private void executeBooleanCommand() {
            ofRequestCommand(WrapperCmd.bool2bool).request();
            ofRequestCommand(WrapperCmd.boolValue2boolValue).request();
            ofRequestCommand(WrapperCmd.boolList2boolList).request();
            ofRequestCommand(WrapperCmd.boolValueList2boolValueList).request();
        }

        private void initBooleanCommand() {
            BoolValue boolValue = new BoolValue();
            boolValue.value = false;

            ofCommand(WrapperCmd.bool2bool).callback(BoolValue.class, result -> {
                BoolValue value = result.getValue();
                log.info("value : {}", value);
            }).setDescription("bool2bool").setRequestData(boolValue);

            ofCommand(WrapperCmd.boolValue2boolValue).callback(BoolValue.class, result -> {
                BoolValue value = result.getValue();
                log.info("value : {}", value);
            }).setDescription("boolValue2boolValue").setRequestData(boolValue);


            BoolValueList boolValueList = BoolValueList.of(List.of(false, true));
            ofCommand(WrapperCmd.boolList2boolList).callback(BoolValueList.class, result -> {
                List<Boolean> value = result.toList(Boolean.class);
                log.info("value : {}", value);
            }).setDescription("boolList2boolList").setRequestData(boolValueList);

            ofCommand(WrapperCmd.boolValueList2boolValueList).callback(BoolValueList.class, result -> {
                List<Boolean> value = result.toList(Boolean.class);
                log.info("value : {}", value);
            }).setDescription("boolValueList2boolValueList").setRequestData(boolValueList);
        }

    }
}
