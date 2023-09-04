package com.seiryuu.gameServer.domain.player;

import com.seiryuu.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 玩家角色对象 player_character
 *
 * @author Seiryuu
 * @date 2023-01-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PlayerCharacter extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    private String characterId;

    /** 玩家id */
    private String playerId;

    /** 地图id */
    private Long mapId;

    /** 地图坐标x */
    private Long mapPostX;

    /** 地图坐标y */
    private Long mapPostY;

    /** 地图坐标z */
    private Long mapPostZ;

    /** 角色栏位 */
    private Long characterField;

    /** 角色名称 */
    private String characterName;

    /** 角色职业 */
    private String characterOccupation;

    /** 角色等级 */
    private Long characterLevel;

    /** 角色当前经验值 */
    private Long characterExp;

    /** 下一级经验值 */
    private Long characterExpNext;

    /** 称号id */
    private String characterTitleId;

    /** 称号 */
    private String characterTitle;

    /** 种族 */
    private String characterRace;

    /** 行会id */
    private String guildId;

    /** 行会名称 */
    private String guildName;

    /** 金币数量 */
    private Long coin;

    /** 灵石数量 */
    private Long magicStone;

    /** 力量 */
    private Long strength;

    /** 敏捷 */
    private Long agility;

    /** 耐力 */
    private Long endurance;

    /** 灵活 */
    private Long flexible;

    /** 智慧 */
    private Long wisdom;

    /** 体质 */
    private Long constitution;

    /** 魅力 */
    private Long charm;

    /** 运气 */
    private Long luck;

    /** 生命值 */
    private Long hitPoint;

    /** 最大生命值 */
    private Long hitPointMax;

    /** 生命值回复速度 */
    private Long hpRecovery;

    /** 法力值 */
    private Long magicPoint;

    /** 最大法力值 */
    private Long magicPointMax;

    /** 法力回复速度 */
    private Long magicRecovery;

    /** 攻击力 */
    private Long attack;

    /** 攻击速度 */
    private Long attackSpeed;

    /** 攻击成功率 */
    private Long attackSuccessRate;

    /** 魔法攻击力 */
    private Long magicAttack;

    /** 施法速度 */
    private Long magicAttackSpeed;

    /** 魔法成功率 */
    private Long magicAttackSuccessRate;

    /** 暴击率 */
    private Long criticalStrike;

    /** 魔法暴击率 */
    private Long magicCriticalStrike;

    /** 命中率 */
    private Long accuracy;

    /** 魔法命中率 */
    private Long magicAccuracy;

    /** 回避率 */
    private Long dodge;

    /** 魔法回避率 */
    private Long magicDodge;

    /** 防御力 */
    private Long defense;

    /** 光抗 */
    private Long lightDefense;

    /** 冰抗 */
    private Long iceDefense;

    /** 火抗 */
    private Long fireDefense;

    /** 雷抗 */
    private Long rayDefense;

    /** 风抗 */
    private Long windDefense;

    /** 暗抗 */
    private Long darkDefense;

    /** 删除状态(0正常 1删除) */
    private String delFlag;
}