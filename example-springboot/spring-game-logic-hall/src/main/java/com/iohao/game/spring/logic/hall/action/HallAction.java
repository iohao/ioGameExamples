/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2023 double joker （262610965@qq.com） . All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
}


