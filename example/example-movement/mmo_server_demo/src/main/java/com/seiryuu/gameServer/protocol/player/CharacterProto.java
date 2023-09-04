package com.seiryuu.gameServer.protocol.player;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.iohao.game.widget.light.protobuf.ProtoFileMerge;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * 玩家角色对象 player_character
 *
 * @author Seiryuu
 * @date 2023-01-07
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
@ProtobufClass
@FieldDefaults(level = AccessLevel.PUBLIC)
@ProtoFileMerge(fileName = "characterProto.proto", filePackage = "protocol.player")
public class CharacterProto {

    /**
     * 角色id
     */
    String characterId;

    /**
     * 玩家id
     */
    String playerId;

    /**
     * 地图id
     */
    Long mapId;

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

    /**
     * 角色栏位
     */
    Long characterField;

    /**
     * 角色名称
     */
    String characterName;

    /**
     * 角色职业
     */
    String characterOccupation;

    /**
     * 角色等级
     */
    Long characterLevel;

    /**
     * 角色当前经验值
     */
    Long characterExp;

    /**
     * 下一级经验值
     */
    Long characterExpNext;

    /**
     * 称号id
     */
    String characterTitleId;

    /**
     * 称号
     */
    String characterTitle;

    /**
     * 种族
     */
    String characterRace;

    /**
     * 行会id
     */
    String guildId;

    /**
     * 行会名称
     */
    String guildName;

    /**
     * 金币数量
     */
    Long coin;

    /**
     * 灵石数量
     */
    Long magicStone;

    /**
     * 力量
     */
    Long strength;

    /**
     * 敏捷
     */
    Long agility;

    /**
     * 耐力
     */
    Long endurance;

    /**
     * 灵活
     */
    Long flexible;

    /**
     * 智慧
     */
    Long wisdom;

    /**
     * 体质
     */
    Long constitution;

    /**
     * 魅力
     */
    Long charm;

    /**
     * 运气
     */
    Long luck;

    /**
     * 生命值
     */
    Long hitPoint;

    /**
     * 最大生命值
     */
    Long hitPointMax;

    /**
     * 生命值回复速度
     */
    Long hpRecovery;

    /**
     * 法力值
     */
    Long magicPoint;

    /**
     * 最大法力值
     */
    Long magicPointMax;

    /**
     * 法力回复速度
     */
    Long magicRecovery;

    /**
     * 攻击力
     */
    Long attack;

    /**
     * 攻击速度
     */
    Long attackSpeed;

    /**
     * 攻击成功率
     */
    Long attackSuccessRate;

    /**
     * 魔法攻击力
     */
    Long magicAttack;

    /**
     * 施法速度
     */
    Long magicAttackSpeed;

    /**
     * 魔法成功率
     */
    Long magicAttackSuccessRate;

    /**
     * 暴击率
     */
    Long criticalStrike;

    /**
     * 魔法暴击率
     */
    Long magicCriticalStrike;

    /**
     * 命中率
     */
    Long accuracy;

    /**
     * 魔法命中率
     */
    Long magicAccuracy;

    /**
     * 回避率
     */
    Long dodge;

    /**
     * 魔法回避率
     */
    Long magicDodge;

    /**
     * 防御力
     */
    Long defense;

    /**
     * 光抗
     */
    Long lightDefense;

    /**
     * 冰抗
     */
    Long iceDefense;

    /**
     * 火抗
     */
    Long fireDefense;

    /**
     * 雷抗
     */
    Long rayDefense;

    /**
     * 风抗
     */
    Long windDefense;

    /**
     * 暗抗
     */
    Long darkDefense;
}