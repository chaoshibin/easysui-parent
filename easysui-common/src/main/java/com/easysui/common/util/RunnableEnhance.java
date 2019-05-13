package com.easysui.common.util;

/**
 * Copyright (C), 2018-2019, 深圳市xxx科技有限公司
 *
 * @author: CHAO
 * Date:     2018/10/29 17:59
 * Description:
 */
public interface RunnableEnhance extends Runnable {

    /**
     * 自启动
     */
    default void start() {
        new Thread(this).start();
    }
}
