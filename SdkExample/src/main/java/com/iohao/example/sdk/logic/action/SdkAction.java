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
package com.iohao.example.sdk.logic.action;

import com.iohao.example.sdk.logic.data.*;
import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.core.doc.DocumentMethod;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.action.skeleton.protocol.wrapper.*;
import com.iohao.game.bolt.broker.client.kit.ExternalCommunicationKit;
import com.iohao.game.common.kit.RandomKit;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import net.datafaker.providers.base.Name;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * My SdkAction
 *
 * @author 渔民小镇
 * @date 2024-11-01
 */
@Slf4j
@ActionController(SdkCmd.cmd)
public final class SdkAction {
    static final Name name = new Faker(Locale.getDefault()).name();
    AtomicInteger counter = new AtomicInteger();

    /**
     * user login
     *
     * @param verifyMessage loginVerify
     * @param flowContext   flowContext
     * @return User info
     */
    @ActionMethod(SdkCmd.loginVerify)
    public UserMessage loginVerify(LoginVerifyMessage verifyMessage, FlowContext flowContext) {
        UserMessage userMessage = ofUserMessage(verifyMessage);

        ExternalCommunicationKit.forcedOffline(userMessage.userId);

        // 绑定 userId，表示登录
        var success = flowContext.bindingUserId(userMessage.userId);
        SdkGameCodeEnum.loginError.assertTrue(success);

        return userMessage;
    }

    /**
     * test int
     *
     * @param value value
     * @return int value
     */
    @ActionMethod(SdkCmd.intValue)
    public int intValue(int value, FlowContext flowContext) {
        var msgId = flowContext.getHeadMetadata().getMsgId();
        log.info("msgId : {}", msgId);
        return value + counter.getAndIncrement();
    }

    /**
     * test long
     *
     * @param value value
     * @return long value
     */
    @ActionMethod(SdkCmd.longValue)
    public long longValue(long value) {
        return value - counter.getAndIncrement();
    }

    /**
     * test boolean
     *
     * @param value value
     * @return boolean value
     */
    @ActionMethod(SdkCmd.boolValue)
    public boolean boolValue(boolean value) {
        return !value;
    }

    /**
     * test String
     *
     * @param value value
     * @return String value
     */
    @ActionMethod(SdkCmd.stringValue)
    public String stringValue(String value) {
        return value + counter.getAndIncrement();
    }

    /**
     * test Object；测试单个对象的接收与响应。
     *
     * @param loginVerifyMessage loginVerify；登录对象。
     * @return UserMessage；用户数据。
     */
    @ActionMethod(SdkCmd.value)
    public UserMessage value(LoginVerifyMessage loginVerifyMessage) {
        return ofUserMessage(loginVerifyMessage);
    }

    private UserMessage ofUserMessage(LoginVerifyMessage loginVerifyMessage) {
        long userId = Long.parseLong(loginVerifyMessage.jwt);
        return ofUserMessage(userId);
    }

    private UserMessage ofUserMessage(long userId) {
        UserMessage userMessage = new UserMessage();
        userMessage.userId = userId;
        userMessage.name = name.fullName();
        return userMessage;
    }

    /**
     * test int list
     *
     * @param value value
     * @return int list
     */
    @ActionMethod(SdkCmd.listInt)
    public List<Integer> listInt(List<Integer> value) {
        return value.stream()
                .map(i -> i + counter.getAndIncrement())
                .toList();
    }

    /**
     * test Long list
     *
     * @param value value
     * @return Long list
     */
    @ActionMethod(SdkCmd.listLong)
    public List<Long> listLong(List<Long> value) {
        return value.stream()
                .map(i -> i - counter.getAndIncrement())
                .toList();
    }

    /**
     * test Boolean list
     *
     * @param value value
     * @return Boolean list
     */
    @ActionMethod(SdkCmd.listBool)
    public List<Boolean> listBool(List<Boolean> value) {
        return value.stream()
                .map(i -> !i)
                .toList();
    }

    /**
     * test String list
     *
     * @param value value
     * @return String list
     */
    @ActionMethod(SdkCmd.listString)
    public List<String> listString(List<String> value) {
        return value.stream()
                .map(i -> i + counter.getAndIncrement())
                .toList();
    }

    /**
     * test Object list
     *
     * @param value LoginVerifyMessage list
     * @return UserMessage list
     */
    @ActionMethod(SdkCmd.listValue)
    public List<UserMessage> listValue(List<LoginVerifyMessage> value) {
        return value.stream()
                .map(this::ofUserMessage)
                .toList();
    }

    /**
     * test error code
     *
     * @param value If the value is equal to 2, an error will be thrown
     * @return int
     */
    @ActionMethod(SdkCmd.testError)
    public int testError(int value) {
        SdkGameCodeEnum.testError.assertTrueThrows(value == 2);
        return value;
    }

    /**
     * test broadcast
     *
     * @param flowContext flowContext
     */
    @ActionMethod(SdkCmd.triggerBroadcast)
    public void triggerBroadcast(FlowContext flowContext) {
        BulletMessage bulletMessage = new BulletMessage();
        bulletMessage.bulletId = counter.getAndIncrement();
        flowContext.broadcastMe(SdkCmd.of(SdkCmd.triggerBroadcast), bulletMessage);

        // single value
        flowContext.broadcastMe(SdkCmd.of(SdkCmd.broadcastInt), IntValue.of(counter.getAndIncrement()));
        flowContext.broadcastMe(SdkCmd.of(SdkCmd.broadcastLong), LongValue.of(counter.getAndIncrement()));
        flowContext.broadcastMe(SdkCmd.of(SdkCmd.broadcastBool), BoolValue.of(RandomKit.randomBoolean()));
        flowContext.broadcastMe(SdkCmd.of(SdkCmd.broadcastString), StringValue.of(counter.getAndIncrement() + ""));
        flowContext.broadcastMe(SdkCmd.of(SdkCmd.broadcastValue), ofUserMessage(counter.getAndIncrement()));

        // list value
        flowContext.broadcastMe(SdkCmd.of(SdkCmd.broadcastListInt), IntValueList.of(List.of(counter.getAndIncrement())));
        flowContext.broadcastMe(SdkCmd.of(SdkCmd.broadcastListLong), LongValueList.of(List.of(counter.longValue())));
        flowContext.broadcastMe(SdkCmd.of(SdkCmd.broadcastListBool), BoolValueList.of(List.of(RandomKit.randomBoolean())));
        flowContext.broadcastMe(SdkCmd.of(SdkCmd.broadcastListString), StringValueList.of(List.of(counter.getAndIncrement() + "")));

        var userList = IntStream.range(1, 3).mapToObj(this::ofUserMessage).toList();
        flowContext.broadcastMe(SdkCmd.of(SdkCmd.broadcastListValue), ByteValueList.ofList(userList));
    }

    /**
     * noParam method test. 没有参数的方法测试
     *
     * @return counter
     */
    @ActionMethod(SdkCmd.noParam)
    public int noParam() {
        return counter.getAndIncrement();
    }

    /**
     * noReturn method test. 没有返回值的方法测试
     *
     * @param name name
     */
    @ActionMethod(SdkCmd.noReturn)
    @DocumentMethod("noReturnMethod")
    public void noReturn(String name) {
        counter.getAndIncrement();
    }

    @ActionMethod(SdkCmd.internalAddMoney)
    public int internalAddMoney(int money) {
        return RandomKit.randomInt(money);
    }
}