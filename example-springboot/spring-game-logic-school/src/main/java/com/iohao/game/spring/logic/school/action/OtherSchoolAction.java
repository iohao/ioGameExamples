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
package com.iohao.game.spring.logic.school.action;

import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.protocol.wrapper.LongPb;
import com.iohao.game.spring.common.cmd.OtherSchoolCmdModule;
import com.iohao.game.spring.common.cmd.SchoolCmdModule;
import com.iohao.game.spring.common.pb.OtherVerify;
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
    @ActionMethod(OtherSchoolCmdModule.longPbWrapper)
    public long longPbWrapper(long levelLong) {
        log.info("levelLong 碎片协议 {}", levelLong);
        return levelLong + 2;
    }

    /**
     * 业务参数自动装箱、拆箱基础类型，解决碎片协议问题
     *
     * @param levelLong 等级
     * @return level
     */
    @ActionMethod(OtherSchoolCmdModule.longPbWrapperLonger)
    public long longPbWrapperLonger(Long levelLong) {
        log.info("levelLong 碎片协议 {}", levelLong);
        return levelLong + 2;
    }

    /**
     * 业务参数自动装箱、拆箱基础类型，解决碎片协议问题
     *
     * @param levelLong 等级
     * @return level
     */
    @ActionMethod(OtherSchoolCmdModule.longPbWrapperLongPb)
    public long longPbWrapperLongPb(LongPb levelLong) {
        log.info("levelLong 碎片协议 {}", levelLong);
        return levelLong.longValue + 2;
    }
}
