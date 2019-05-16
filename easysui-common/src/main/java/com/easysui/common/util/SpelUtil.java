package com.easysui.common.util;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Map;

/**
 * @author CHAO 2019/5/14 12:57
 */
public final class SpelUtil {

    public static String parseValue(String expression, String[] parameterNames, Object[] args) {
        if (ArrayUtils.isEmpty(args)) {
            return expression;
        }
        Map<String, Object> argMap = Maps.newHashMapWithExpectedSize(args.length);
        for (int i = 0; i < parameterNames.length; i++) {
            argMap.put(parameterNames[i], args[i]);
        }
        return parseValue(expression, argMap);
    }


    /**
     * @param expression spel表达式
     * @param argMap     key = 参数名, value参数值
     * @return 解析参数值
     */
    public static String parseValue(String expression, Map<String, Object> argMap) {
        if (!StringUtils.startsWith(expression, "#")) {
            return expression;
        }
        //使用SPEL进行key的解析
        ExpressionParser parser = new SpelExpressionParser();
        //创建SPEL上下文
        EvaluationContext evaluationContext = new StandardEvaluationContext();
        //把方法参数放入SPEL上下文中
        for (String key : argMap.keySet()) {
            evaluationContext.setVariable(key, argMap.get(key));
        }
        return parser.parseExpression(expression).getValue(evaluationContext, String.class);
    }
}