package com.seiryuu.gameServer.hooks;

import cn.hutool.core.convert.Convert;
import com.iohao.game.external.core.aware.UserSessionsAware;
import com.iohao.game.external.core.hook.UserHook;
import com.iohao.game.external.core.session.UserSession;
import com.iohao.game.external.core.session.UserSessions;
import com.seiryuu.gameServer.managers.MapManager;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Seiryuu
 * @since 2023-09-02 14:26
 * 自定义实现 用户上线 下线钩子
 */
@Slf4j
public class MyUserHook implements UserHook, UserSessionsAware {

    UserSessions<?, ?> userSessions;

    @Override
    public void setUserSessions(UserSessions<?, ?> userSessions) {
        this.userSessions = userSessions;
    }

    @Override
    public void into(UserSession userSession) {
        long userId = userSession.getUserId();
        log.info("[玩家上线] 在线数量:{}  userId:{} -- {}", this.userSessions.countOnline(), userId, userSession.getUserChannelId());
    }

    @Override
    public void quit(UserSession userSession) {
        long userId = userSession.getUserId();
        log.info("[玩家下线] 在线数量:{}  userId:{} -- {}",
                userSessions.countOnline()
                , userId, userSession.getUserChannelId());

        // 找到map 移除 并通知 有玩家下线
        MapManager.INSTANCE.getGameMap("1").characterLeave(Convert.toStr(userId));
    }
}
