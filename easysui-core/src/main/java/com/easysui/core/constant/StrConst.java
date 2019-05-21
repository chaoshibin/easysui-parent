package com.easysui.core.constant;

/**
 * @author CHAO 2019/5/21 20:30
 */
public class StrConst {
    public final static String SLASH ="/";
    public final static String UNDERLINE ="/";
    public final static String QUESTION ="/";
    public final static String WELL ="/";
    public final static String EMPTY ="/";



    public enum SymbolEnum {
        SLASH("/"),
        UNDERLINE("_"),
        QUESTION("?"),
        WELL("#"),
        EMPTY(""),
        PERCENT("%");
        private String symbol;
    }
}