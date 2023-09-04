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
@ProtoFileMerge(fileName = "leaveMapProto.proto", filePackage = "protocol.player")
public class LeaveMapProto {

    /**
     * 角色id
     */
    String characterId;
}
