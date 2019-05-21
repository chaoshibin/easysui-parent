package com.easysui.core.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author CHAO 2019/5/21 12:47
 */
@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SymbolEnum {
    SLASH("/"),
    UNDERLINE("_"),
    QUESTION("?"),
    WELL("#"),
    EMPTY(""),
    PERCENT("%");
    private String symbol;
}
