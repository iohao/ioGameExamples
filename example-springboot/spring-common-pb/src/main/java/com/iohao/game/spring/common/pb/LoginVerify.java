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
package com.iohao.game.spring.common.pb;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.iohao.game.spring.common.SpringGameProtoFile;
import com.iohao.game.widget.light.protobuf.ProtoFileMerge;
import lombok.AccessLevel;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * 登录信息
 *
 * @author 渔民小镇
 * @date 2022-07-27
 */
@ToString
@ProtobufClass
@FieldDefaults(level = AccessLevel.PUBLIC)
@ProtoFileMerge(fileName = SpringGameProtoFile.COMMON_FILE_NAME, filePackage = SpringGameProtoFile.COMMON_FILE_PACKAGE)
public class LoginVerify {
    /** jwt */
    String jwt;

    /**
     * 登录业务码
     * <pre>
     *     注意，这个业务码放这里只是为了方便测试多种情况
     *     交由测试请求端来控制
     *
     *     ------------业务码说明------------------
     *
     *     当业务码为 【0】时 (相当于号已经在线上了，不能重复登录)
     *     如果游戏对外服已经有该玩家的连接了，就抛异常，告诉请求端玩家已经在线。
     *     否则就正常登录成功
     *
     *     当业务码为 【1】时 （相当于顶号）
     *     强制断开之前的客户端连接，并让本次登录成功。
     * </pre>
     */
    int loginBizCode;
}
