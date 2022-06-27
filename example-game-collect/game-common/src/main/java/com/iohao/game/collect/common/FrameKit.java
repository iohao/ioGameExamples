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
package com.iohao.game.collect.common;

import lombok.experimental.UtilityClass;

/**
 * 帧间隔计算工具
 *
 * @author 渔民小镇
 * @date 2022-01-15
 */
@UtilityClass
public class FrameKit {
    /** 帧率 给前端发送消息的间隔时间(单位:毫秒) */
    public static final int FRAME_RATE = 50;

    /** 1 秒时间 */
    public static final int SECONDS = 1000;

    /** 1 分钟: 60 * 1000 */
    public static final int MINUTE = 60 * SECONDS;


    /**
     * 每秒总帧数
     * <pre>
     *     填写 60 表示每秒运行 60 帧
     * </pre>
     */
    int FRAME_COUNT = 15;

    /**
     * 帧间隔 (帧速 = 每秒/总帧数)
     *
     * @param frameCount 每秒总帧数 , 填写 60 表示每秒运行 60 帧
     * @return 间隔时间
     */
    public int framePerMillis(int frameCount) {
        return 1000 / frameCount;
    }


}
