package com.seiryuu.gameServer.protocol.map;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.iohao.game.widget.light.protobuf.ProtoFileMerge;
import com.seiryuu.gameServer.protocol.player.CharacterProto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * @author Seiryuu
 * @description 同步地图玩家消息
 * @since 2023-08-30 8:01
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
@ProtobufClass
@FieldDefaults(level = AccessLevel.PUBLIC)
@ProtoFileMerge(fileName = "syncMapPlayerProto.proto", filePackage = "protocol.player")
public class SyncMapPlayerProto {

    List<CharacterProto> playerCharacterList;
}
