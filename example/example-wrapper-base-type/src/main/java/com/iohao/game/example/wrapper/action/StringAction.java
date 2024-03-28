/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - present double joker （262610965@qq.com） . All Rights Reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
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
