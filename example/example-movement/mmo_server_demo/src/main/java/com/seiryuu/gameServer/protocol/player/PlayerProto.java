package com.seiryuu.gameServer.protocol.player;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.iohao.game.widget.light.protobuf.ProtoFileMerge;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * @author 渔民小镇
 * @date 2022-05-28
 */
@Builder
@ToString
@ProtobufClass
@FieldDefaults(level = AccessLevel.PUBLIC)
@ProtoFileMerge(fileName = "playerInfoProto.proto", filePackage = "protocol.player")
public class PlayerProto {

    /**
     * playerId
     */
    String id;

    /**
     * 令牌
     */
    String token;

    /**
     * 角色列表
     */
    List<CharacterProto> characterList;
}