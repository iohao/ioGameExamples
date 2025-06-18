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
package com.iohao.game.spring.logic.classes.action;

import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.core.CmdInfo;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.action.skeleton.core.flow.MyFlowContext;
import com.iohao.game.action.skeleton.protocol.wrapper.StringValue;
import com.iohao.game.spring.common.cmd.ClassesCmdModule;
import com.iohao.game.spring.common.cmd.IssuesCmdModule;
import com.iohao.game.spring.common.data.MyAttachment;
import com.iohao.game.spring.common.pb.ClassesPb;
import com.iohao.game.spring.common.pb.SchoolPb;
import com.iohao.game.spring.logic.classes.service.ClassesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@AllArgsConstructor
@ActionController(ClassesCmdModule.cmd)
public class ClassesAction {
    ClassesService classesService;

    @ActionMethod(ClassesCmdModule.issu143)
    public StringValue issu143(FlowContext flowContext) {
        classesService.helloSpring();
        // https://github.com/iohao/ioGame/issues/143
        // 逻辑服A（非spring管理的action）想跟逻辑服B(spring管理的action)通信
        CmdInfo cmdInfo = CmdInfo.of(IssuesCmdModule.cmd, IssuesCmdModule.the143Result);
        StringValue stringValue = flowContext.invokeModuleMessage(cmdInfo).getData(StringValue.class);
        log.info("stringValue : {}", stringValue.value);
        return stringValue;
    }

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
         * 相关文档 https://iohao.github.io/game/docs/core/jsr380
         */

        log.info("jsr380Class : {}", schoolPb);
    }

    @ActionMethod(ClassesCmdModule.printAttachment)
    public void printAttachment(MyFlowContext flowContext) {
        MyAttachment attachment = flowContext.getAttachment();
        log.info("attachment : \n{}", attachment);
    }
}
