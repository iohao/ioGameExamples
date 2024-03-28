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
package com.iohao.game.spring.logic.school.action;

import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.core.CmdInfo;
import com.iohao.game.action.skeleton.core.commumication.BroadcastContext;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.action.skeleton.protocol.wrapper.LongValue;
import com.iohao.game.bolt.broker.core.client.BrokerClientHelper;
import com.iohao.game.spring.common.cmd.OtherSchoolCmdModule;
import com.iohao.game.spring.common.pb.OtherVerify;
import com.iohao.game.spring.common.pb.SchoolPb;
import com.iohao.game.spring.common.pb.UserInfo;
import com.iohao.game.spring.logic.school.annotation.IgnoreDebugInout;
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
    @IgnoreDebugInout
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
    public UserInfo longValueWithBroadcast(FlowContext flowContext) {

        SchoolPb schoolPb = new SchoolPb();
        schoolPb.email = "hello";

        CmdInfo cmdInfo = CmdInfo.of(OtherSchoolCmdModule.cmd, OtherSchoolCmdModule.longValueWithBroadcastData);
        flowContext.broadcast(cmdInfo, schoolPb);

        UserInfo userInfo = new UserInfo();
        userInfo.id = 800;
        return userInfo;
    }
}
