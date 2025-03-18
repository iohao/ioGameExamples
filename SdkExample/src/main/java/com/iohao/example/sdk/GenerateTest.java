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
package com.iohao.example.sdk;

import com.iohao.example.sdk.logic.data.SdkGameCodeEnum;
import com.iohao.game.action.skeleton.core.doc.CsharpDocumentGenerate;
import com.iohao.game.action.skeleton.core.doc.DocumentAccessAuthentication;
import com.iohao.game.action.skeleton.core.doc.IoGameDocumentHelper;
import com.iohao.game.action.skeleton.core.doc.TypeScriptDocumentGenerate;
import com.iohao.game.bolt.broker.client.BrokerClientStartup;
import com.iohao.game.external.core.config.ExternalGlobalConfig;
import com.iohao.game.widget.light.protobuf.ProtoGenerateFile;

import java.util.Locale;

/**
 * @author 渔民小镇
 * @date 2024-11-02
 * @since 21.20
 */
public final class GenerateTest {
    // setting root path
    static String rootPath = "/Users/join/gitme/ioGame-sdk/";

    public static void main(String[] args) {
        // CHINA or US
        Locale.setDefault(Locale.CHINA);

        /*
         * GameExternalServer accessAuthentication
         * 游戏对外服访问权限，不生成权限控制的 action
         */
        SdkApplication.extractedAccess();
        DocumentAccessAuthentication reject = ExternalGlobalConfig.accessAuthenticationHook::reject;
        IoGameDocumentHelper.setDocumentAccessAuthentication(reject);

        // Load the business framework of each gameLogicServer
        // 加载游戏逻辑服的业务框架
        SdkApplication.listLogic().forEach(BrokerClientStartup::createBarSkeleton);

        /*
         * Generate actions, broadcasts, and error codes.
         * cn: 生成 action、广播、错误码
         */

        // About generating TypeScript code
//        generateCodeVue();
//        generateCodeAngular();
//        generateCodeHtml();
        generateCocosCreator();

        // About generating C# code
//        generateCodeCsharpGodot();
//        generateCodeCsharpUnity();

        // Added an enumeration error code class to generate error code related information
        IoGameDocumentHelper.addErrorCodeClass(SdkGameCodeEnum.class);
        // Generate document
        IoGameDocumentHelper.generateDocument();

        // Generate .proto
        generateProtoFile();
    }

    static void generateProtoFile() {
        /*
         * .proto generate
         * document https://www.yuque.com/iohao/game/vpe2t6
         */

        // setting CamelCase style
        // 与类属性同名 风格（java 一般是驼峰）。
        // ProtoGenerateSetting.setFieldNameFunction(FieldNameGenerate::getFieldName);

        var protoGenerateFile = new ProtoGenerateFile()
                // By default, it will be generated in the target/proto directory
                // .proto 默认生成的目录为 target/proto
//                .setGenerateFolder("/Users/join/gitme/game/MyGames/proto")
                // The package name to be scanned
                // 需要扫描的包名
                .addProtoPackage("com.iohao.example.sdk.logic.data");

        // .proto generate
        // 生成 .proto 文件
        protoGenerateFile.generate();
    }

    private static void generateCodeCsharpUnity() {
        var documentGenerate = new CsharpDocumentGenerate();
        // 设置代码生成所存放的路径，如果不做任何设置，将会生成在 target/code 目录中
        // By default, it will be generated in the target/code directory
        String path = rootPath + "ioGameSdkCsharpExampleUnity/Assets/Scripts/Gen/Code";
        documentGenerate.setPath(path);

        // Your .proto path: Set the import path of common_pb in C#
        // see target/proto/common.proto package
        // documentGenerate.setProtoImportPath("using Pb.Common;");

        IoGameDocumentHelper.addDocumentGenerate(documentGenerate);
    }

    private static void generateCodeCsharpGodot() {
        var documentGenerate = new CsharpDocumentGenerate();
        // 设置代码生成所存放的路径，如果不做任何设置，将会生成在 target/code 目录中
        // By default, it will be generated in the target/code directory
        String path = rootPath + "ioGameSdkCsharpExampleGodot/script/gen/code";
        documentGenerate.setPath(path);

        // Your .proto path: Set the import path of common_pb in C#
        // see target/proto/common.proto package
        // documentGenerate.setProtoImportPath("using Pb.Common;");
        IoGameDocumentHelper.addDocumentGenerate(documentGenerate);
    }

    private static void generateCodeVue() {
        var documentGenerate = new TypeScriptDocumentGenerate();

        // 设置代码生成所存放的路径，如果不做任何设置，将会生成在 target/code 目录中
        // By default, it will be generated in the target/code directory
        String path = rootPath + "ioGameSdkTsExampleVue/src/assets/gen/code";
        documentGenerate.setPath(path);

        // Your .proto path: Set the import path of common_pb in Vue.
        documentGenerate.setProtoImportPath("../common_pb");

        IoGameDocumentHelper.addDocumentGenerate(documentGenerate);
    }

    private static void generateCodeHtml() {
        var documentGenerate = new TypeScriptDocumentGenerate();

        // 设置代码生成所存放的路径，如果不做任何设置，将会生成在 target/code 目录中
        // By default, it will be generated in the target/code directory
        String path = rootPath + "ioGameSdkTsExampleHtml/src/assets/gen/code";
        documentGenerate.setPath(path);

        // Your .proto path: Set the import path of common_pb in Vue.
        documentGenerate.setProtoImportPath("../common_pb");

        IoGameDocumentHelper.addDocumentGenerate(documentGenerate);
    }

    private static void generateCocosCreator() {
        var documentGenerate = new TypeScriptDocumentGenerate();

        // 设置代码生成所存放的路径，如果不做任何设置，将会生成在 target/code 目录中
        // By default, it will be generated in the target/code directory
        String path = rootPath + "ioGameSdkTsExampleCocos/assets/scripts/gen/code";
        documentGenerate.setPath(path);

        // Your .proto path: Set the import path of common_pb in CocosCreator
        documentGenerate.setProtoImportPath("db://assets/scripts/gen/common_pb");

        IoGameDocumentHelper.addDocumentGenerate(documentGenerate);
    }

    private static void generateCodeAngular() {
        var documentGenerate = new TypeScriptDocumentGenerate();

        // 设置代码生成所存放的路径，如果不做任何设置，将会生成在 target/code 目录中
        // By default, it will be generated in the target/code directory
        String path = rootPath + "ioGameSdkTsExampleAngular/src/assets/gen/code";
        documentGenerate.setPath(path);

        // Your .proto path: Set the import path of common_pb in Vue.
        documentGenerate.setProtoImportPath("../common_pb");

        IoGameDocumentHelper.addDocumentGenerate(documentGenerate);
    }
}
