package com.easysui.core.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * <p>
 *
 * </p>
 *
 * @author CHAO
 * @since 2019/7/11
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TimeService {

    public static long getCurrentMillis() {
        return System.currentTimeMillis();
    }
}