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
