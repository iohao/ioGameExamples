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
        newLongValue.value = longValue.value + 2;
        return newLongValue;
    }

    @ActionMethod(WrapperCmd.long2longList)
    public List<Long> long2longList(long value) {
        List<Long> list = new ArrayList<>();
        list.add(value);
        list.add(value + 1);
        return list;
    }

    @ActionMethod(WrapperCmd.long2longValueList)
    public LongValueList long2longValueList(long value) {
        List<Long> list = new ArrayList<>();
        list.add(value);
        list.add(value + 1);

        LongValueList longValueList = new LongValueList();
        longValueList.values = list;

        return longValueList;
    }

    @ActionMethod(WrapperCmd.longValueList2longList)
    public List<Long> longValueList2longList(LongValueList longValueList) {
        List<Long> list = new ArrayList<>();
        list.add(11L);
        list.add(22L);
        return list;
    }

    @ActionMethod(WrapperCmd.longList2longValueList)
    public LongValueList longList2longValueList(List<Long> longList) {
        List<Long> list = new ArrayList<>();
        list.add(11L);
        list.add(22L);

        LongValueList longValueList = new LongValueList();
        longValueList.values = list;

        return longValueList;
    }

}
