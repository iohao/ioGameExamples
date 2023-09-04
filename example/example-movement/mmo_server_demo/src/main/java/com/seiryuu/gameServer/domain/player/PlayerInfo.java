package com.seiryuu.gameServer.domain.player;

import com.seiryuu.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Seiryuu
 * @since 2023-01-01 20:38
 * 系统用户表(注册用户)
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PlayerInfo extends BaseEntity {

    /**
     * 玩家id
     */
    private String playerId;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户账号
     */
    private String playerAccount;

    /**
     * 用户密码
     */
    private String playerPassword;

    /**
     * 用户状态(0正常 1停用)
     */
    private String playerStatus;

    /**
     * 手机号
     */
    private String phoneNum;

    /**
     * 邀请码
     */
    private String invitationCode;

    /**
     * 删除状态(0正常 1删除)
     */
    private String delFlag;
}
