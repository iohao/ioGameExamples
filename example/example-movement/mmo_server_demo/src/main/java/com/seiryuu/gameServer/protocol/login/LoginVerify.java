package com.seiryuu.gameServer.protocol.login;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.iohao.game.widget.light.protobuf.ProtoFileMerge;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * @author 渔民小镇
 * @date 2022-05-28
 */
@Builder
@ToString
@ProtobufClass
@FieldDefaults(level = AccessLevel.PUBLIC)
@ProtoFileMerge(fileName = "loginVerify.proto", filePackage = "message.loginVerify")
public class LoginVerify {

    /**
     * 登录账号
     */
    String account;

    /**
     * 登录密码
     */
    String password;
}