package com.easysui.core.util;

import com.easysui.core.enums.SymbolEnum;
import com.google.common.base.Joiner;

/**
 * @author CHAO 2019/5/21 12:29
 */
public class StringFormatUtils {
    /**
     * 下划线分割
     */
    private static final Joiner JOINER_UNDERLINE = Joiner.on(SymbolEnum.UNDERLINE.getSymbol()).useForNull("");
    /**
     * 斜杠分割
     */
    private static final Joiner JOINER_SLASH = Joiner.on(SymbolEnum.SLASH.getSymbol()).useForNull("");
    private static final String S_S = "%s_%s";

    public static String format(SymbolEnum symbolEnum, String... args) {
        return Joiner.on(symbolEnum.getSymbol()).useForNull(SymbolEnum.EMPTY.getSymbol()).join(args);
    }

    public static String formatLine(String... args) {
        return JOINER_UNDERLINE.join(args);
    }

    public static String formatSlash(String... args) {
        return JOINER_SLASH.join(args);
    }

    public static String format(String prefix, String suffix) {
        return String.format(S_S, prefix, suffix);
    }
}