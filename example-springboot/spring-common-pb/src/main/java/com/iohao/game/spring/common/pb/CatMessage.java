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
package com.iohao.game.spring.common.pb;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.AccessLevel;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * @author 渔民小镇
 * @date 2022-08-03
 */
@ToString
@ProtobufClass
//@EnableZigZap
@FieldDefaults(level = AccessLevel.PUBLIC)
public class CatMessage {
    /** 请求命令类型: 0 心跳，1 业务 */
//    @Protobuf(fieldType = FieldType.INT32, order = 1)
    int cmdCode;
    /** 协议开关，用于一些协议级别的开关控制，比如 安全加密校验等。 : 0 不校验 */
//    @Protobuf(fieldType = FieldType.INT32, order = 2)

    int protocolSwitch;
    /** 业务路由（高16为主, 低16为子） */
//    @Protobuf(fieldType = FieldType.INT32, order = 3)
    int cmdMerge;


//    @Protobuf(fieldType = FieldType.SINT32, order = 4)
//    int responseStatus;
//    /** 验证信息 */
//    @Protobuf(fieldType = FieldType.STRING, order = 5)
//    String validMsg;
//    @Protobuf(fieldType = FieldType.BYTES, order = 6)
//    byte[] data;

}
