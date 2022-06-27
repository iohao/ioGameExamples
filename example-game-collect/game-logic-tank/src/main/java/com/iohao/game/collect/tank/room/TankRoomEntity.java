/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2022 double joker （262610965@qq.com） . All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License..
 */
package com.iohao.game.collect.tank.room;

import com.iohao.game.collect.common.FrameKit;
import com.iohao.game.widget.light.room.AbstractRoom;
import com.iohao.game.widget.light.room.AbstractFlowContextSend;
import com.iohao.game.collect.proto.tank.TankLocation;
import com.iohao.game.collect.tank.send.TankSend;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import lombok.AccessLevel;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.jctools.maps.NonBlockingHashMap;

import java.io.Serial;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 坦克 房间
 *
 * @author 渔民小镇
 * @date 2022-01-14
 */
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TankRoomEntity extends AbstractRoom {
    @Serial
    private static final long serialVersionUID = 5354718988786951601L;
    /** 帧同步 id */
    int frameId;

    /** 房间最大帧数 */
    final int maxFrameId;
    /**
     * <pre>
     *     key : frame
     * </pre>
     */
    final Map<Integer, CopyOnWriteArrayList<TankLocation>> moveMap = new NonBlockingHashMap<>();

    public TankRoomEntity(int maxFrameId) {
        this.maxFrameId = maxFrameId;
    }

    public TankRoomEntity() {
        this.maxFrameId = 60 * FrameKit.MINUTE;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <T extends AbstractFlowContextSend> T createSend(FlowContext flowContext) {
        return (T) new TankSend(flowContext);
    }
}
