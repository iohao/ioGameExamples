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
package com.iohao.game.collect.tank.room.flow;

import com.iohao.game.widget.light.room.CommonRuleInfo;
import com.iohao.game.widget.light.room.RuleInfo;
import com.iohao.game.widget.light.room.flow.RoomRuleInfoCustom;
import com.iohao.game.action.skeleton.core.exception.MsgException;

/**
 * 坦克游戏规则
 *
 * @author 渔民小镇
 * @date 2022-01-14
 */
public class TankRoomRuleInfoCustom implements RoomRuleInfoCustom {
    static final CommonRuleInfo commonRuleInfo = new CommonRuleInfo();

    @Override
    public RuleInfo getRuleInfo(String ruleInfoJson) throws MsgException {
        // 暂时没什么特殊规则，不做 json 解析，使用默认的空规则
        return commonRuleInfo;
    }
}
