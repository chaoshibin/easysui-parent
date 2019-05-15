package com.easysui.cache.aspect;

import com.easysui.cache.annotation.EasyCacheExpire;
import com.easysui.cache.annotation.EasyCachePut;
import com.easysui.common.util.AspectUtil;
import com.easysui.common.util.SpelUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author CHAO 2019/4/16
 */
@Slf4j
@Aspect
public class CacheAspect {

    @Pointcut("@annotation(com.easysui.cache.annotation.EasyCachePut)")
    public void cachePutPointCut() {
    }

    @Pointcut("@annotation(com.easysui.cache.annotation.EasyCacheExpire)")
    public void cacheExpirePointCut() {
    }

    @Around("cachePutPointCut()")
    public Object cachePutProcess(ProceedingJoinPoint joinPoint) throws Throwable {
        //注解
        EasyCachePut annotation = AspectUtil.getMethod(joinPoint).getAnnotation(EasyCachePut.class);
        //缓存名
        String cacheName = annotation.cacheName();
        //缓存key
        String cacheKey = this.getCacheKey(joinPoint, annotation.key());

        //TODO redis取缓存
        Object obj = null;
        CacheInfo cacheInfo = CacheInfo.builder().cacheName(cacheName).cacheKey(cacheKey).expireSeconds(annotation.expire()).build();
        return obj != null ? obj : this.doCachePutProceed(joinPoint, cacheInfo);
    }

    private String getCacheKey(ProceedingJoinPoint joinPoint, String[] keys) {
        if (ArrayUtils.isEmpty(keys)) {
            throw new IllegalArgumentException("key值表达式错误");
        }
        String cacheKey;
        if (1 == ArrayUtils.getLength(keys)) {
            cacheKey = SpelUtil.parseValue(keys[0], AspectUtil.getMethodSignature(joinPoint).getParameterNames(), joinPoint.getArgs());
        } else {
            cacheKey = Arrays.stream(keys).map(key -> SpelUtil.parseValue(key, AspectUtil.getMethodSignature(joinPoint).getParameterNames(), joinPoint.getArgs()))
                    .collect(Collectors.joining("_"));
        }
        return cacheKey;
    }

    @Around("cacheExpirePointCut()")
    public Object cacheExpireProcess(ProceedingJoinPoint joinPoint) throws Throwable {
        //注解
        EasyCacheExpire annotation = AspectUtil.getMethod(joinPoint).getAnnotation(EasyCacheExpire.class);
        //缓存名
        String cacheName = annotation.cacheName();
        //缓存key
        String cacheKey = this.getCacheKey(joinPoint, annotation.key());
        //TODO 删除缓存

        return joinPoint.proceed();
    }

    private Object doCachePutProceed(ProceedingJoinPoint joinPoint, CacheInfo cacheInfo) throws Throwable {
        Object result = joinPoint.proceed();
        //TODO 设置缓存
        return result;
    }

    @Setter
    @Getter
    @Builder
    private static class CacheInfo {
        private String cacheName;
        private String cacheKey;
        private long expireSeconds;
    }
}
