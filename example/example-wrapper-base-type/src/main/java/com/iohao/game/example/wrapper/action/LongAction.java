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
import com.iohao.game.action.skeleton.protocol.wrapper.LongPb;
import com.iohao.game.action.skeleton.protocol.wrapper.LongListPb;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 渔民小镇
 * @date 2022-06-26
 */
@ActionController(WrapperCmd.cmd)
public class LongAction {
    @ActionMethod(WrapperCmd.long2long)
    public long long2long(long longValue) {
        return longValue + 1;
    }

    @ActionMethod(WrapperCmd.longPb2longPb)
    public LongPb longPb2longPb(LongPb longPb) {
        LongPb newLongPb = new LongPb();
        newLongPb.longValue = longPb.longValue + 2;
        return newLongPb;
    }

    @ActionMethod(WrapperCmd.long2longList)
    public List<Long> long2longList(long longValue) {
        List<Long> list = new ArrayList<>();
        list.add(longValue);
        list.add(longValue + 1);
        return list;
    }

    @ActionMethod(WrapperCmd.long2longListPb)
    public LongListPb long2longListPb(long longValue) {
        List<Long> list = new ArrayList<>();
        list.add(longValue);
        list.add(longValue + 1);

        LongListPb longListPb = new LongListPb();
        longListPb.longValues = list;

        return longListPb;
    }

    @ActionMethod(WrapperCmd.longListPb2longList)
    public List<Long> longListPb2longList(LongListPb longListPb) {
        List<Long> list = new ArrayList<>();
        list.add(11L);
        list.add(22L);
        return list;
    }

    @ActionMethod(WrapperCmd.longList2longListPb)
    public LongListPb longList2longListPb(List<Long> longList) {
        List<Long> list = new ArrayList<>();
        list.add(11L);
        list.add(22L);

        LongListPb longListPb = new LongListPb();
        longListPb.longValues = list;

        return longListPb;
    }

}
