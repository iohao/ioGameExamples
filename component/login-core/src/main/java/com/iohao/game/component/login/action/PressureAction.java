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
package com.iohao.game.component.login.action;

import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.common.kit.InternalKit;
import com.iohao.game.component.login.cmd.LoginCmd;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.LongAdder;

/**
 * @author 渔民小镇
 * @date 2023-07-16
 */
@Slf4j
@ActionController(LoginCmd.cmd)
public class PressureAction {
    static final LongAdder inc = new LongAdder();

    @ActionMethod(LoginCmd.inc)
    public void inc() {
        inc.increment();
    }

    static {
        // 每秒打印一次
        InternalKit.newTimeoutSeconds(new TimerTask() {
            @Override
            public void run(Timeout timeout) {
                long value = inc.longValue();
                log.info("  inc {}", value);
                InternalKit.newTimeoutSeconds(this);
            }
        });
    }
}
