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
import com.iohao.game.action.skeleton.protocol.wrapper.StringValueList;
import com.iohao.game.action.skeleton.protocol.wrapper.StringValue;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 渔民小镇
 * @date 2023-02-05
 */
@ActionController(WrapperCmd.cmd)
public class StringAction {
    @ActionMethod(WrapperCmd.string2string)
    public String string2string(String s) {
        return s + 1;
    }

    @ActionMethod(WrapperCmd.stringValue2stringValue)
    public StringValue stringValue2stringValue(StringValue stringValue) {
        StringValue newStringValue = new StringValue();
        newStringValue.value = stringValue.value + 1;
        return newStringValue;
    }

    @ActionMethod(WrapperCmd.stringList2stringList)
    public List<String> stringList2stringList(List<String> stringList) {
        List<String> list = new ArrayList<>();
        list.add(11L + "");
        list.add(22L + "");
        return list;
    }

    @ActionMethod(WrapperCmd.stringValueList2stringValueList)
    public StringValueList stringValueList2stringValueList(StringValueList stringValueList) {
        List<String> list = new ArrayList<>();
        list.add(11L + "");
        list.add(22L + "");

        StringValueList newStringValueList = new StringValueList();
        newStringValueList.values = list;

        return newStringValueList;
    }


}
