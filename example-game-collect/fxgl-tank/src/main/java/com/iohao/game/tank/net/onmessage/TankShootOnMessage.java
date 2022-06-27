package com.iohao.game.tank.net.onmessage;

import com.iohao.game.collect.proto.tank.TankBullet;
import com.iohao.game.collect.tank.TankCmd;
import com.iohao.game.tank.net.TankOnMessage;
import com.iohao.game.action.skeleton.core.CmdKit;
import com.iohao.game.common.kit.ProtoKit;
import com.iohao.game.bolt.broker.client.external.bootstrap.message.ExternalMessage;

/**
 * @author 渔民小镇
 * @date 2022-03-06
 */
public class TankShootOnMessage implements TankOnMessage {

    @Override
    public int getCmdMerge() {
        return CmdKit.merge(TankCmd.cmd, TankCmd.shooting);
    }

    @Override
    public Object response(ExternalMessage externalMessage, byte[] data) {
        TankBullet tankBullet = ProtoKit.parseProtoByte(data, TankBullet.class);

        return tankBullet;
    }

    private TankShootOnMessage() {

    }

    public static TankShootOnMessage me() {
        return Holder.ME;
    }

    /** 通过 JVM 的类加载机制, 保证只加载一次 (singleton) */
    private static class Holder {
        static final TankShootOnMessage ME = new TankShootOnMessage();
    }
}
