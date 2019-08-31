package com.easysui.common.util;

import lombok.SneakyThrows;

/**
 * 功能描述:
 * <p/>
 *
 * @author CHAO 新增日期：2018/6/23
 * @author CHAO 修改日期：2018/6/23
 * @version 1.0.0
 * @since 1.0.0
 */
public final class ClassUtil {
    private ClassUtil() {
    }

    @SneakyThrows
    public static <T> Class<? extends T> loadClass(String className, Class<T> clazz) {
        return Class.forName(className).asSubclass(clazz);
    }
}
