/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2023 double joker （262610965@qq.com） . All Rights Reserved.
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
package com.iohao.game.spring.common;

import com.iohao.game.action.skeleton.core.DataCodecKit;
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


        byte[] bytes = DataCodecKit.encode(loginVerify);

        for (int i = 0; i < bytes.length; i++) {
            log.info("i : {} {}", i, bytes[i]);

        }
        log.info("bytes : {}", bytes);
    }
}
