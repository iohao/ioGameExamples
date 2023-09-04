package com.seiryuu.gameServer.protocol.movement;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.iohao.game.widget.light.protobuf.ProtoFileMerge;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * @author Seiryuu
 * @description 玩家移动通讯协议
 * @since 2023-08-29 8:22
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
@ProtobufClass
@FieldDefaults(level = AccessLevel.PUBLIC)
@ProtoFileMerge(fileName = "playerMoveProto.proto", filePackage = "protocol.player")
public class PlayerMoveProto {

    /**
     * 角色id
     */
    String characterId;

    /**
     * 坐标x
     */
    float x;

    /**
     * 坐标y
     */
    float y;

    /**
     * 坐标z
     */
    float z;

    /**
     * 坐标r
     */
    float r;
}
