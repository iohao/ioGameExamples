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
import com.iohao.game.action.skeleton.protocol.wrapper.IntValueList;
import com.iohao.game.action.skeleton.protocol.wrapper.IntValue;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 渔民小镇
 * @date 2022-06-26
 */
@ActionController(WrapperCmd.cmd)
public class IntAction {
    @ActionMethod(WrapperCmd.int2int)
    public int int2int(int value) {
        return value + 1;
    }

    @ActionMethod(WrapperCmd.intValue2intValue)
    public IntValue intValue2intValue(IntValue intValue) {
        IntValue newIntValue = new IntValue();
        newIntValue.value = intValue.value + 2;
        return newIntValue;
    }

    @ActionMethod(WrapperCmd.int2intList)
    public List<Integer> int2intList(int value) {
        List<Integer> list = new ArrayList<>();
        list.add(value);
        list.add(value + 1);
        return list;
    }

    @ActionMethod(WrapperCmd.int2intValueList)
    public IntValueList int2intValueList(int value) {
        List<Integer> list = new ArrayList<>();
        list.add(value);
        list.add(value + 1);

        IntValueList intValueList = new IntValueList();
        intValueList.values = list;

        return intValueList;
    }

    @ActionMethod(WrapperCmd.intValueList2intList)
    public List<Integer> intValueList2intList(IntValueList intValueList) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        return list;
    }

    @ActionMethod(WrapperCmd.intList2intValueList)
    public IntValueList intList2intValueList(List<Integer> intList) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);

        IntValueList intValueList = new IntValueList();
        intValueList.values = list;

        return intValueList;
    }

}
