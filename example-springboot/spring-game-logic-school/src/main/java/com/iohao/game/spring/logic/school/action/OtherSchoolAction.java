/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2023 double joker （262610965@qq.com） . All Rights Reserved.
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
import com.iohao.game.action.skeleton.protocol.wrapper.LongValue;
import com.iohao.game.bolt.broker.core.client.BrokerClientHelper;
import com.iohao.game.spring.common.cmd.OtherSchoolCmdModule;
import com.iohao.game.spring.common.pb.OtherVerify;
import com.iohao.game.spring.common.pb.SchoolPb;
import com.iohao.game.spring.common.pb.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author 渔民小镇
 * @date 2022-08-26
 */
@Slf4j
@Component
@ActionController(OtherSchoolCmdModule.cmd)
public class OtherSchoolAction {

    /**
     * 更新学校信息，jsr380
     *
     * @param otherVerify otherVerify
     */
    @ActionMethod(OtherSchoolCmdModule.jsr380)
    public void otherVerify(OtherVerify otherVerify) {
        log.info("jsr380 : {}", otherVerify);
    }


    /**
     * 业务参数自动装箱、拆箱基础类型，解决碎片协议问题
     *
     * @param levelLong 等级
     * @return level
     */
    @ActionMethod(OtherSchoolCmdModule.longValueWrapper)
    public long longValueWrapper(long levelLong) {
        log.info("levelLong 碎片协议 {}", levelLong);
        return levelLong + 2;
    }

    /**
     * 业务参数自动装箱、拆箱基础类型，解决碎片协议问题
     *
     * @param levelLong 等级
     * @return level
     */
    @ActionMethod(OtherSchoolCmdModule.longValueWrapperLonger)
    public long longValueWrapperLonger(Long levelLong) {
        log.info("levelLong 碎片协议 {}", levelLong);
        return levelLong + 2;
    }

    /**
     * 业务参数自动装箱、拆箱基础类型，解决碎片协议问题
     *
     * @param levelLong 等级
     * @return level
     */
    @ActionMethod(OtherSchoolCmdModule.longValueWrapperLongValue)
    public long longValueWrapperLongValue(LongValue levelLong) {
        log.info("levelLong 碎片协议 {}", levelLong);
        return levelLong.value + 2;
    }

    @ActionMethod(OtherSchoolCmdModule.longValueWithBroadcast)
    public UserInfo longValueWithBroadcast() {

        SchoolPb schoolPb = new SchoolPb();
        schoolPb.email = "hello";

        CmdInfo cmdInfo = CmdInfo.getCmdInfo(OtherSchoolCmdModule.cmd, OtherSchoolCmdModule.longValueWithBroadcastData);

        BroadcastContext broadcastContext = BrokerClientHelper.getBroadcastContext();
        broadcastContext.broadcast(cmdInfo, schoolPb);

        UserInfo userInfo = new UserInfo();
        userInfo.id = 800;
        return userInfo;
    }
}
