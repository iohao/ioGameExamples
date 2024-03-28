/*
 * ioGame
 * Copyright (C) 2021 - present  渔民小镇 （262610965@qq.com、luoyizhu@gmail.com） . All Rights Reserved.
 * # iohao.com . 渔民小镇
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.iohao.game.example.endpoint.animal.action;

import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.common.kit.RandomKit;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 渔民小镇
 * @date 2023-06-06
 */
@Slf4j
@ActionController(DemoCmdForEndPointAnimal.cmd)
public class AnimalAction {
    /**
     * 随机一个动物
     *
     * @return 动物
     */
    @ActionMethod(DemoCmdForEndPointAnimal.randomAnimal)
    public String randomAnimal() {
        int i = RandomKit.randomInt(100) % 3;
        return switch (i) {
            case 0 -> "三文鱼";
            case 1 -> "北极熊";
            default -> "大雁";
        };
    }
}
