package com.easysui.core.util;

import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author CHAO 2019/4/16
 */
public class AspectUtil {

    /**
     * 获取注解方法
     *
     * @param jp
     * @return
     */
    public static Method getMethod(JoinPoint jp) {
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        return methodSignature.getMethod();
    }

    /**
     * 获取方法注解
     *
     * @param jp              链接点
     * @param annotationClass 注解类
     * @param <T>             注解
     * @return
     */
    public static <T extends Annotation> T getAnnotationOnMethod(JoinPoint jp, Class<T> annotationClass) {
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        return methodSignature.getMethod().getAnnotation(annotationClass);
    }

    public static MethodSignature getMethodSignature(JoinPoint jp) {
        return (MethodSignature) jp.getSignature();
    }

    /**
     * 获取方法名
     *
     * @param jp
     * @return
     */
    public static String getMethodName(JoinPoint jp) {
        MethodSignature signature = (MethodSignature) jp.getSignature();
        return signature.getDeclaringTypeName() + "." + signature.getName();
    }

    /**
     * 连接字符
     *
     * @param joinPoint
     * @param values
     * @return
     */
    public static String contactValue(JoinPoint joinPoint, String[] values) {
        if (ArrayUtils.isEmpty(values)) {
            throw new IllegalArgumentException("key值表达式错误");
        }
        String result;
        if (1 == ArrayUtils.getLength(values)) {
            result = SpelUtil.parseValue(values[0], AspectUtil.getMethodSignature(joinPoint).getParameterNames(), joinPoint.getArgs());
        } else {
            result = Arrays.stream(values).map(key -> SpelUtil.parseValue(key, AspectUtil.getMethodSignature(joinPoint).getParameterNames(), joinPoint.getArgs()))
                    .collect(Collectors.joining("_"));
        }
        return result;
    }

    /**
     * 获取返回类型
     *
     * @param jp
     * @return
     */
    public static Class<?> getReturnType(JoinPoint jp) {
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        return methodSignature.getReturnType();
    }


    /**
     * 构建返回结果
     *
     * @param clazz         返回类型
     * @param codeFieldName 错误码域
     * @param msgFieldName  错误信息域
     * @param code          错误码
     * @param msg           错误信息
     * @param <T>           返回结果
     * @return
     */
    public static <T> T buildResult(Class<T> clazz, String codeFieldName, String msgFieldName, String code, String msg) {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            T obj = constructor.newInstance();
            Field codeField = clazz.getDeclaredField(codeFieldName);
            codeField.setAccessible(true);
            codeField.set(obj, code);

            Field msgField = clazz.getDeclaredField(msgFieldName);
            msgField.setAccessible(true);
            msgField.set(obj, msg);
            return obj;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取接口名
     *
     * @param jp
     * @return
     */
    public static String getInterfaceName(JoinPoint jp) {
        return jp.getSignature().getDeclaringTypeName();
    }
}
