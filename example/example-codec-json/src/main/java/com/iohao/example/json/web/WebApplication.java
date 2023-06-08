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
package com.iohao.example.json.web;

import lombok.extern.slf4j.Slf4j;
import org.noear.solon.Solon;

/**
 * @author 渔民小镇
 * @date 2023-02-01
 */
@Slf4j
public class WebApplication {
    public static void start(int serverPort) {

        Solon.start(WebApplication.class, new String[]{"server.port=" + serverPort}, app -> {

            //在后端把 / 变成 /index.html
            app.filter((ctx, chain) -> {
                if ("/".equals(ctx.pathNew())) {
                    ctx.pathNew("/index.html");
                }

                chain.doFilter(ctx);
            });

        });
    }
}
