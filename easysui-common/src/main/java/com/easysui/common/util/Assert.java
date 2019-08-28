package com.easysui.common.util;

import org.apache.commons.lang3.StringUtils;

/**
 * <p/>
 *
 * @author CHAO
 * @since 2018/2/11
 */
public final class Assert {
    private Assert() {
    }

    public static <T> void isNull(T object, String argument) {
        if (object == null) {
            throw new IllegalArgumentException(argument);
        }
        if ((object instanceof String) && StringUtils.isBlank(String.valueOf(object))) {
            throw new IllegalArgumentException(argument);
        }
    }
}
