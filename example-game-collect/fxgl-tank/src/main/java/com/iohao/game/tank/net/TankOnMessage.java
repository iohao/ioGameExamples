package com.iohao.game.tank.net;

import com.iohao.game.action.skeleton.core.flow.codec.ProtoDataCodec;
import com.iohao.game.bolt.broker.client.external.bootstrap.message.ExternalMessage;
import com.iohao.game.bolt.broker.client.external.bootstrap.message.ExternalMessageCmdCode;

/**
 * @author 渔民小镇
 * @date 2022-03-06
 */
public interface TankOnMessage {

    int getCmdMerge();

    Object response(ExternalMessage externalMessage, byte[] data);

    default void request(Object data) {
        this.request(data, null);
    }

    default void request(Object data, Runnable runnable) {
        ExternalMessage externalMessage = this.createExternalMessage();

        byte[] bytes = ProtoDataCodec.me().encode(data);
        // 业务数据
        externalMessage.setData(bytes);

        TankWebsocketClient.me().request(externalMessage);

        if (runnable != null) {
            TankWebsocketClient.me().getActionMap().put(this.getCmdMerge(), runnable);
        }
    }


    private ExternalMessage createExternalMessage() {

        ExternalMessage request = new ExternalMessage();
        request.setCmdCode(ExternalMessageCmdCode.biz);
        request.setProtocolSwitch(0);

        request.setCmdMerge(this.getCmdMerge());

        return request;
    }


}
