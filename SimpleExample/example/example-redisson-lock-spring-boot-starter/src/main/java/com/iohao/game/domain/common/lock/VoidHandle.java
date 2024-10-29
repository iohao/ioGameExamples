package com.iohao.game.domain.common.lock;

/**
 * 带返回值的执行接口
 * <pre>
 *  后期可以放到conmmon中，再所有存在回调的地方使用
 * </pre>
 *
 * @author shen
 * @date 2022-03-28
 * @Slogan 慢慢变好，是给自己最好的礼物
 */
public interface VoidHandle {
    void execute();
}
