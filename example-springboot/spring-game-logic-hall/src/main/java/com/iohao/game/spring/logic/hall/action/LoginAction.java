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
package com.iohao.game.spring.logic.hall.action;

import com.github.javafaker.Faker;
import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.core.exception.MsgException;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.bolt.broker.client.kit.ExternalCommunicationKit;
import com.iohao.game.bolt.broker.client.kit.UserIdSettingKit;
import com.iohao.game.spring.common.SpringGameCodeEnum;
import com.iohao.game.spring.common.cmd.HallCmdModule;
import com.iohao.game.spring.common.data.MyAttachment;
import com.iohao.game.spring.common.pb.LoginVerify;
import com.iohao.game.spring.common.pb.UserInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.Locale;

/**
 * 登录相关的
 *
 * @author 渔民小镇
 * @date 2022-07-27
 * <pre>
 *     注意，这个 Action 没有使用 spring 管理
 *
 *     参考:
 *     https://www.yuque.com/iohao/game/evkgnz#FD7s1
 * </pre>
 */
@Slf4j
@ActionController(HallCmdModule.cmd)
public class LoginAction {
    /** 测试神器，假数据 */
    static Faker faker = new Faker(Locale.CHINA);

    /**
     * 登录业务
     *
     * @param loginVerify loginVerify
     * @param flowContext flowContext
     * @return UserInfo
     * @throws MsgException e
     */
    @ActionMethod(HallCmdModule.loginVerify)
    public UserInfo loginVerify(LoginVerify loginVerify, FlowContext flowContext) throws MsgException {
        // 登录业务码
        int loginBizCode = loginVerify.loginBizCode;

        // 通过 jwt，得到用户（玩家）数据
        UserInfo userInfo = getUserInfoByJwt(loginVerify.jwt);
        userInfo.tempInt = loginVerify.age;
        userInfo.time = loginVerify.time;

        long userId = userInfo.id;

        if (loginBizCode == 0) {
            // (相当于号已经在线上了，不能重复登录)
            log.info("号已经在线上了，不能重复登录");

            // 查询用户是否在线
            boolean existUser = ExternalCommunicationKit.existUser(userId);
            log.info("用户是否在线 : {} - userId : {}", existUser, userId);

            // 如果账号在线，就抛异常 （断言 + 异常机制）
            SpringGameCodeEnum.accountOnline.assertTrueThrows(existUser);

        } else if (loginBizCode == 1) {
            log.info("顶号 userId:{}", userId);
            // （相当于顶号），强制断开之前的客户端连接，并让本次登录成功。
            ExternalCommunicationKit.forcedOffline(userId);
        }

        // channel 中设置用户的真实 userId；
        boolean success = UserIdSettingKit.settingUserId(flowContext, userId);

        // 失败抛异常码 （断言 + 异常机制）
        SpringGameCodeEnum.loginError.assertTrue(success);

        return userInfo;
    }

    @ActionMethod(HallCmdModule.attachment)
    public void attachment(FlowContext flowContext) {
        // 创建自定义的元附加信息对象
        MyAttachment myAttachment = new MyAttachment();
        myAttachment.userId = flowContext.getUserId();
        myAttachment.nickname = "英雄无敌3";

        // 设置元信息 ----- 关键代码
        ExternalCommunicationKit.setAttachment(myAttachment);
        log.info("设置元信息 : {}", myAttachment);
    }

    @ActionMethod(HallCmdModule.attachmentPrint)
    public MyAttachment printAttachment(FlowContext flowContext) {
        // 得到元信息，这个是在上面的方法中设置的元信息对象
        var attachment = flowContext.getAttachment(MyAttachment.class);
        log.info("打印元信息 attachment : {}", attachment);
        return attachment;
    }

    private UserInfo getUserInfoByJwt(String jwt) {
        // hash jwt 当作 userId，不访问 DB 了
        int userId = Math.abs(jwt.hashCode());

        UserInfo userInfo = new UserInfo();
        userInfo.id = userId;
        userInfo.name = faker.name().firstName();
        userInfo.tempInt = 273676;
        log.info("userInfo : {}", userInfo);

        return userInfo;
    }
}
