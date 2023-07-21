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
package com.iohao.game.spring.common;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.system.SystemUtil;
import com.iohao.game.widget.light.protobuf.ProtoGenerateFile;

import java.io.File;
import java.util.regex.Matcher;

/**
 * @author 渔民小镇
 * @date 2022-07-10
 */
public class SpringGenerateFileForProto {
    public static void main(String[] args) {

        /*
         * .proto 文件生成
         * 相关文档 https://www.yuque.com/iohao/game/vpe2t6
         *
         * 运行该类，会在当前项目 target/proto 目录下生成 .proto 文件
         */

        // 需要扫描的包名
        String protoPackagePath = SpringGenerateFileForProto.class.getPackageName();

        String[] protoSourcePathArray = new String[]{
                SystemUtil.getUserInfo().getCurrentDir()
                , "example-springboot"
                , "spring-common-pb"
                , "src"
                , "main"
                , "java"
                , protoPackagePath.replaceAll("\\.", Matcher.quoteReplacement(File.separator))
        };

        // 源码目录
        String protoSourcePath = ArrayUtil.join(protoSourcePathArray, File.separator);

        String[] generateFolderArray = new String[]{
                SystemUtil.getUserInfo().getCurrentDir()
                , "example-springboot"
                , "spring-common-pb"
                , "target"
                , "proto"
        };

        // 生成 .proto 文件存放的目录
        String generateFolder = ArrayUtil.join(generateFolderArray, File.separator);

        ProtoGenerateFile protoGenerateFile = ProtoGenerateFile.builder()
                // 源码目录
                .protoSourcePath(protoSourcePath)
                // 需要扫描的包名
                .protoPackagePath(protoPackagePath)
                // 生成 .proto 文件存放的目录
                .generateFolder(generateFolder)
                .build();

        // 生成 .proto 文件
        protoGenerateFile.generate();
    }
}
