/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2022 double joker （262610965@qq.com） . All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License..
 */
package com.iohao.game.example.one.action;

import com.iohao.game.example.one.code.DemoCodeEnum;
import com.iohao.game.example.common.msg.HelloReq;
import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.core.exception.MsgException;

/**
 * 示例action
 *
 * @author 渔民小镇
 * @date 2022-02-24
 */
@ActionController(DemoCmd.cmd)
public class DemoAction {
    /**
     * 示例 here 方法
     *
     * @param helloReq helloReq
     * @return HelloReq
     */
    @ActionMethod(DemoCmd.here)
    public HelloReq here(HelloReq helloReq) {
        HelloReq newHelloReq = new HelloReq();
        newHelloReq.name = helloReq.name + ", I'm here ";
        return newHelloReq;
    }

    /**
     * 示例 异常机制演示
     *
     * @param helloReq helloReq
     * @return HelloReq
     * @throws MsgException e
     */
    @ActionMethod(DemoCmd.jackson)
    public HelloReq jackson(HelloReq helloReq) throws MsgException {
        String jacksonName = "jackson";

        DemoCodeEnum.jackson_error.assertTrue(jacksonName.equals(helloReq.name));

        helloReq.name = helloReq.name + ", hello, jackson !";

        return helloReq;
    }
}
