package com.iohao.game.spring.common;

import com.iohao.game.common.kit.ArrayKit;
import com.iohao.game.widget.light.protobuf.ProtoGenerateFile;

import java.io.File;

/**
 * @author 渔民小镇
 * @date 2023-10-25
 */
public class SpringGenerateFileForProtoTest {

    public static void main(String[] args) {
        /*
         * .proto 文件生成
         * 相关文档 https://www.yuque.com/iohao/game/vpe2t6
         *
         * ######################################################
         * 会将项目的 pb 生成到当前项目的 target/proto/common.proto 文件中
         * ######################################################
         */

        // 需要扫描的包名
        String currentDir = System.getProperty("user.dir");

        // 生成 .proto 文件存放的目录
        String generateFolder = ArrayKit.join(new String[]{
                currentDir
                , "target"
                , "proto"
        }, File.separator);

        ProtoGenerateFile protoGenerateFile = ProtoGenerateFile.builder()
                // 源码目录
                .protoSourcePath(currentDir)
                // 需要扫描的包名
                .protoPackagePath("com.iohao.game.spring.common")
                // 生成 .proto 文件存放的目录
                .generateFolder(generateFolder)
                .build();

        // 生成 .proto 文件
        protoGenerateFile.generate();
    }
}