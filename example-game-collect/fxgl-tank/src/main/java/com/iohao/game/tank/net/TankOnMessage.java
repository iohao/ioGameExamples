package com.iohao.game.tank.net;

import com.iohao.game.action.skeleton.core.DataCodecKit;
import com.iohao.game.external.core.config.ExternalGlobalConfig;
import com.iohao.game.external.core.message.ExternalMessage;
import com.iohao.game.external.core.message.ExternalMessageCmdCode;


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

        byte[] bytes = DataCodecKit.encode(data);
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
        // 协议开关，用于一些协议级别的开关控制，比如 安全加密校验等。 : 0 不校验
        request.setProtocolSwitch(ExternalGlobalConfig.protocolSwitch);

        request.setCmdMerge(this.getCmdMerge());

        return request;
    }


}
