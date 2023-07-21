/*
 * ioGame
 * Copyright (C) 2021 - 2023  渔民小镇 （262610965@qq.com、luoyizhu@gmail.com） . All Rights Reserved.
 * # iohao.com . 渔民小镇
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
package com.iohao.game.spring.logic.school.action;

import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.core.CmdInfo;
import com.iohao.game.action.skeleton.core.commumication.InvokeModuleContext;
import com.iohao.game.bolt.broker.core.client.BrokerClientHelper;
import com.iohao.game.spring.common.cmd.ClassesCmdModule;
import com.iohao.game.spring.common.cmd.IssuesCmdModule;
import com.iohao.game.spring.common.pb.ClassesPb;
import com.iohao.game.spring.common.pb.SceneEnterReq;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 渔民小镇
 * @date 2023-06-15
 */
@Slf4j
@ActionController(IssuesCmdModule.cmd)
public class IssuesAction {

    @ActionMethod(IssuesCmdModule.the143)
    public void the143() {
        // https://github.com/iohao/ioGame/issues/143
        // 逻辑服A（非spring管理的action）想跟逻辑服B(spring管理的action)通信
        CmdInfo cmdInfo = CmdInfo.getCmdInfo(ClassesCmdModule.cmd, ClassesCmdModule.getClasses);
        InvokeModuleContext invokeModuleContext = BrokerClientHelper.getInvokeModuleContext();
        ClassesPb classesPb = invokeModuleContext.invokeModuleMessageData(cmdInfo, ClassesPb.class);

        log.info("the143 classesPb : {}", classesPb);
    }

    @ActionMethod(IssuesCmdModule.the143Result)
    public String the143Result() {
        // https://github.com/iohao/ioGame/issues/143
        return "the143Result";
    }

    @ActionMethod(IssuesCmdModule.the147)
    public SceneEnterReq the147(SceneEnterReq sceneEnterReq) {
        log.info("sceneEnterReq : \n{}", sceneEnterReq);

        return sceneEnterReq;
    }
}
