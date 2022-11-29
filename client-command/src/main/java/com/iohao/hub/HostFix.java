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
package com.iohao.hub;

import cn.hutool.core.date.StopWatch;
import cn.hutool.core.io.FileUtil;
import cn.hutool.system.SystemUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * @author 渔民小镇
 * @date 2022-11-27
 */
@Slf4j
public class HostFix {
    public static void main(String[] args) throws MalformedURLException {
        if (!SystemUtil.getOsInfo().isMac()) {
            return;
        }

        StopWatch stopWatch = new StopWatch();
        stopWatch.start("a");

        String netUrl = "https://raw.hellogithub.com/hosts";
        URL url = new URL(netUrl);
        String readString = FileUtil.readString(url, Charset.defaultCharset());

        String defaultValue = getDefaultValue();
        StringBuilder builder = new StringBuilder(defaultValue);
        builder.append(readString);

        log.info("builder : {}", builder);

        String hostFilePath = "/etc/hosts";
        FileUtil.writeString(builder.toString(), hostFilePath, Charset.defaultCharset());

        stopWatch.stop();
        long lastTaskTimeMillis = stopWatch.getLastTaskTimeMillis();
        log.info("lastTaskTimeMillis : {}", lastTaskTimeMillis);

    }

    private static String getDefaultValue() {
        // hosts file default value
        return """
                                
                ##
                # Host Database
                #
                # localhost is used to configure the loopback interface
                # when the system is booting.  Do not change this entry.
                ##
                127.0.0.1	localhost
                255.255.255.255	broadcasthost
                ::1             localhost
                                
                # Added by Docker Desktop
                # To allow the same kube context to work on the host and the container:
                127.0.0.1 kubernetes.docker.internal
                # End of section
                                
                """;
    }
}
