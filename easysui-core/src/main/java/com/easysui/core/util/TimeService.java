package com.easysui.core.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * <p>
 *
 * </p>
 *
 * @author Chao Shibin
 * @since 2019/7/11
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TimeService {

    public static long getCurrentMillis() {
        return System.currentTimeMillis();
    }
}