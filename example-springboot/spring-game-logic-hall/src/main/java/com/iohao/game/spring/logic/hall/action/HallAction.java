/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2023 double joker （262610965@qq.com） . All Rights Reserved.
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
package com.iohao.game.spring.logic.hall.action;

import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.common.kit.ArrayKit;
import com.iohao.game.spring.common.cmd.HallCmdModule;
import com.iohao.game.spring.common.pb.Animal;
import com.iohao.game.spring.common.pb.AnimalType;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author 渔民小镇
 * @date 2023-04-16
 */
@Slf4j
@ActionController(HallCmdModule.cmd)
public class HallAction {
    @ActionMethod(HallCmdModule.testList)
    public List<Animal> listAnimal() {
        // 查询出列表
        return IntStream.range(1, 4).mapToObj(id -> {
            Animal animal = new Animal();
            animal.id = id;
            animal.animalType = ArrayKit.random(AnimalType.values());
            return animal;
        }).collect(Collectors.toList());
    }

    @ActionMethod(HallCmdModule.testEnum)
    public Animal getAnimal(Animal animal) {

        Animal newAnimal = new Animal();
        newAnimal.id = animal.id + 1;
        newAnimal.animalType = animal.animalType;

        return newAnimal;
    }

    @ActionMethod(HallCmdModule.acceptList)
    public List<Animal> acceptList(List<Animal> animals) {
        // 接收 list，返回 list 示例
        for (Animal animal : animals) {
            animal.id = animal.id + 10;
        }

        return animals;
    }
}


