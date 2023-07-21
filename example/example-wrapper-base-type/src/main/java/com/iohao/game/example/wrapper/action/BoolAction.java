/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2023 double joker （262610965@qq.com） . All Rights Reserved.
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
