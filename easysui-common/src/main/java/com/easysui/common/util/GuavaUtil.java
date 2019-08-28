package com.easysui.common.util;

import com.google.common.base.Joiner;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 功能描述:
 * <p/>
 *
 * @author CHAO 新增日期：2018/3/26
 * @author CHAO 修改日期：2018/3/26
 * @version 1.0.0
 * @since 1.0.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GuavaUtil {
    private static final Joiner.MapJoiner JOINER = Joiner.on("&").withKeyValueSeparator("=").useForNull("");

    public static String getHttpParam(Map<?, ?> map) {
        return JOINER.join(map);
    }

    public static String getHttpRequest(String url, Map<?, ?> map) {
        return url + "?" + getHttpParam(map);
    }
}
