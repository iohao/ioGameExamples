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
import com.iohao.game.action.skeleton.protocol.wrapper.BoolValue;
import com.iohao.game.action.skeleton.protocol.wrapper.BoolValueList;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 渔民小镇
 * @date 2023-02-10
 */
@ActionController(WrapperCmd.cmd)
public class BoolAction {
    @ActionMethod(WrapperCmd.bool2bool)
    public boolean bool2bool(boolean value) {
        return value;
    }

    @ActionMethod(WrapperCmd.boolValue2boolValue)
    public BoolValue boolValue2boolValue(BoolValue boolValue) {
        BoolValue newBoolValue = new BoolValue();
        newBoolValue.value = boolValue.value;
        return newBoolValue;
    }

    @ActionMethod(WrapperCmd.boolList2boolList)
    public List<Boolean> boolList2boolList(List<Boolean> booleanList) {
        List<Boolean> list = new ArrayList<>();
        list.add(true);
        list.add(false);
        return list;
    }

    @ActionMethod(WrapperCmd.boolValueList2boolValueList)
    public BoolValueList boolValueList2boolValueList(BoolValueList boolValueList) {
        List<Boolean> list = new ArrayList<>();
        list.add(true);
        list.add(false);

        BoolValueList newBoolValueList = new BoolValueList();
        newBoolValueList.values = list;

        return newBoolValueList;
    }

}
