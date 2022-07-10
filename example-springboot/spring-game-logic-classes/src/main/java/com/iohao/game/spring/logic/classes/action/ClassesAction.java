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
package com.iohao.game.spring.logic.classes.action;

import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.spring.common.cmd.SpringCmdModule;
import com.iohao.game.spring.common.pb.ClassesPb;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 班级相关 action
 *
 * @author 渔民小镇
 * @date 2022-07-10
 */
@Slf4j
@ActionController(SpringCmdModule.ClassesCmd.cmd)
public class ClassesAction {

    /**
     * 得到班级信息；请求、响应
     *
     * @return ClassesPb
     */
    @ActionMethod(SpringCmdModule.ClassesCmd.getClasses)
    public ClassesPb getClasses() {

        log.info("得到班级信息；请求、响应");

        ClassesPb classesPb = new ClassesPb();
        classesPb.studentNum = ThreadLocalRandom.current().nextInt(1, 100);

        return classesPb;
    }

    /**
     * 班级方法；请求、无响应
     *
     * @param classesPb classesPb
     */
    @ActionMethod(SpringCmdModule.ClassesCmd.classesHereVoid)
    public void classesHereVoid(ClassesPb classesPb) {
        
        log.info("班级方法；请求、无响应 : {}", classesPb);
    }
}
