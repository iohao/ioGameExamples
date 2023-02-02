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
package com.iohao.game.spring.logic.classes.action;

import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.spring.common.cmd.ClassesCmdModule;
import com.iohao.game.spring.common.pb.ClassesPb;
import com.iohao.game.spring.common.pb.SchoolPb;
import com.iohao.game.spring.logic.classes.service.ClassesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 班级相关 action
 *
 * @author 渔民小镇
 * @date 2022-07-10
 */
@Slf4j
@Component
@ActionController(ClassesCmdModule.cmd)
public class ClassesAction {
    @Autowired
    ClassesService classesService;

    /**
     * 得到班级信息；请求、响应
     *
     * @return ClassesPb
     */
    @ActionMethod(ClassesCmdModule.getClasses)
    public ClassesPb getClasses() {

        classesService.helloSpring();

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
    @ActionMethod(ClassesCmdModule.classesHereVoid)
    public void classesHereVoid(ClassesPb classesPb) {

        log.info("班级方法；请求、无响应 : {}", classesPb);
    }

    /**
     * 更新学校信息，jsr380Class
     *
     * @param schoolPb schoolPb
     */
    @ActionMethod(ClassesCmdModule.jsr380)
    public void updateSchool(SchoolPb schoolPb) {
        /*
         * 进入业务方法需要满足这么几个条件
         * 1. SchoolPb.email 不能为 null ，并且是合法的电子邮件地址
         * 2. SchoolPb.classCapacity 学校最大教室容量不能超过 100 个
         * 3. SchoolPb.teacherNum 学校老师数量不能少于 60 个
         *
         * 相关文档 https://www.yuque.com/iohao/game/ghng6g
         */

        log.info("jsr380Class : {}", schoolPb);
    }
}
