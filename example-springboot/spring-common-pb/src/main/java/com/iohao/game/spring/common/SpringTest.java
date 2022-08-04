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
package com.iohao.game.spring.common;

import com.iohao.game.common.kit.ProtoKit;
import com.iohao.game.spring.common.pb.LoginVerify;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 渔民小镇
 * @date 2022-08-02
 */
@Slf4j
public class SpringTest {
    public static void main(String[] args) {
        /*

0: 8
1: 152
2: 180
3: 33
4: 18
5: 4
6: 116
7: 101
8: 115
9: 116
10: 24
11: 2
         */
        LoginVerify loginVerify = new LoginVerify();
        loginVerify.age = 273676;
        loginVerify.jwt = "test";
        loginVerify.loginBizCode = 1;


        byte[] bytes = ProtoKit.toBytes(loginVerify);

        for (int i = 0; i < bytes.length; i++) {
            log.info("i : {} {}", i, bytes[i]);

        }
        log.info("bytes : {}", bytes);
    }
}
