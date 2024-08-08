/*
 * ioGame
 * Copyright (C) 2021 - present  渔民小镇 （262610965@qq.com、luoyizhu@gmail.com） . All Rights Reserved.
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
package com.iohao.game.spring;

import com.iohao.game.action.skeleton.core.doc.IoGameDocumentHelper;
import com.iohao.game.bolt.broker.client.BrokerClientStartup;
import com.iohao.game.spring.common.SpringGameCodeEnum;
import com.iohao.game.widget.light.protobuf.kit.GenerateFileKit;

/**
 * @author 渔民小镇
 * @date 2024-07-07
 */
public class TestGenerate {
    public static void main(String[] args) {
        // 加载各逻辑服的业务框架
        SpringGameOneApplication.listLogic().forEach(BrokerClientStartup::createBarSkeleton);

        // ====== 生成对接文档、生成 proto ======

        // 添加枚举错误码 class，用于生成错误码相关信息
        IoGameDocumentHelper.addErrorCodeClass(SpringGameCodeEnum.class);
        // 生成文档
        IoGameDocumentHelper.generateDocument();

        // .proto 文件生成
//        generateProtoFile();
    }

    private static void generateProtoFile() {
        /*
         * .proto 文件生成
         * 相关文档 https://www.yuque.com/iohao/game/vpe2t6
         */

        // 需要扫描的包名
        String packagePath = "com.iohao.game.spring.common";
        GenerateFileKit.generate(packagePath);
    }
}
