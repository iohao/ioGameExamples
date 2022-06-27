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
package com.iohao.game.example.wrapper.action;

import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.protocol.wrapper.IntListPb;
import com.iohao.game.action.skeleton.protocol.wrapper.IntPb;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 渔民小镇
 * @date 2022-06-26
 */
@ActionController(WrapperCmd.cmd)
public class IntAction {
    @ActionMethod(WrapperCmd.int2int)
    public int int2int(int intValue) {
        return intValue + 1;
    }

    @ActionMethod(WrapperCmd.intPb2intPb)
    public IntPb intPb2intPb(IntPb intPb) {
        IntPb newIntPb = new IntPb();
        newIntPb.intValue = intPb.intValue + 2;
        return newIntPb;
    }

    @ActionMethod(WrapperCmd.int2intList)
    public List<Integer> int2intList(int intValue) {
        List<Integer> list = new ArrayList<>();
        list.add(intValue);
        list.add(intValue + 1);
        return list;
    }

    @ActionMethod(WrapperCmd.int2intListPb)
    public IntListPb int2intListPb(int intValue) {
        List<Integer> list = new ArrayList<>();
        list.add(intValue);
        list.add(intValue + 1);

        IntListPb intListPb = new IntListPb();
        intListPb.intValues = list;

        return intListPb;
    }

    @ActionMethod(WrapperCmd.intListPb2intList)
    public List<Integer> intListPb2IntList(IntListPb intListPb) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        return list;
    }

    @ActionMethod(WrapperCmd.intList2intListPb)
    public IntListPb intList2IntListPb(List<Integer> intList) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);

        IntListPb intListPb = new IntListPb();
        intListPb.intValues = list;

        return intListPb;
    }

}
