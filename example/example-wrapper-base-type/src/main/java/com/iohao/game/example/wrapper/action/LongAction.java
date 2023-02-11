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
package com.iohao.game.example.wrapper.action;

import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.protocol.wrapper.LongValue;
import com.iohao.game.action.skeleton.protocol.wrapper.LongValueList;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 渔民小镇
 * @date 2022-06-26
 */
@ActionController(WrapperCmd.cmd)
public class LongAction {
    @ActionMethod(WrapperCmd.long2long)
    public long long2long(long value) {
        return value + 1;
    }

    @ActionMethod(WrapperCmd.longValue2longValue)
    public LongValue longValue2longValue(LongValue longValue) {
        LongValue newLongValue = new LongValue();
        newLongValue.value = longValue.value + 1;
        return newLongValue;
    }

    @ActionMethod(WrapperCmd.longList2longList)
    public List<Long> longList2longList(List<Long> longList) {
        List<Long> list = new ArrayList<>();
        list.add(1L);
        list.add(2L);
        return list;
    }

    @ActionMethod(WrapperCmd.longValueList2longValueList)
    public LongValueList LongValueList2longValueList(LongValueList longValueList) {
        List<Long> list = new ArrayList<>();
        list.add(1L);
        list.add(2L);

        LongValueList newLongValueList = new LongValueList();
        newLongValueList.values = list;

        return newLongValueList;
    }


}
