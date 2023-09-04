package com.seiryuu.gameServer.protocol.map;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.iohao.game.widget.light.protobuf.ProtoFileMerge;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * @author Seiryuu
 * @description
 * @since 2023-08-30 8:30
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
@ProtobufClass
@FieldDefaults(level = AccessLevel.PUBLIC)
@ProtoFileMerge(fileName = "enterMapProto.proto", filePackage = "protocol.player")
public class EnterMapProto {

    /**
     * 角色id
     */
    String characterId;

    /**
     * 朝向
     */
    float orientation;

    /**
     * 地图坐标x
     */
    float mapPostX;

    /**
     * 地图坐标y
     */
    float mapPostY;

    /**
     * 地图坐标z
     */
    float mapPostZ;
}
