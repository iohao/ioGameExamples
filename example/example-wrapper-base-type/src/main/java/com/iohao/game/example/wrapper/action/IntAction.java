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
        newIntValue.value = intValue.value + 1;
        return newIntValue;
    }

    @ActionMethod(WrapperCmd.intList2intList)
    public List<Integer> intList2intList(List<Integer> intList) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        return list;
    }

    @ActionMethod(WrapperCmd.intValueList2intValueList)
    public IntValueList intValueList2intValueList(IntValueList intValueList) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);

        IntValueList newIntValueList = new IntValueList();
        newIntValueList.values = list;

        return newIntValueList;
    }
}
