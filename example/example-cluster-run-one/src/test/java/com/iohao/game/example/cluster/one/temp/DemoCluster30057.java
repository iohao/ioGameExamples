/*
 * ioGame
 * Copyright (C) 2021 - 2023  渔民小镇 （262610965@qq.com、luoyizhu@gmail.com） . All Rights Reserved.
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
package com.iohao.game.example.cluster.one.temp;


import io.scalecube.cluster.Cluster;

import java.util.concurrent.TimeUnit;

import static com.iohao.game.example.cluster.one.temp.DemoClusterKit.getCluster;

/**
 * @author 渔民小镇
 * @date 2023-05-26
 */
public class DemoCluster30057 {
  public static void main(String[] args) throws InterruptedException {
    Cluster alice = getCluster("Bob 30057", 30057);

    System.out.println(alice);

    TimeUnit.MINUTES.sleep(10);

  }
}
