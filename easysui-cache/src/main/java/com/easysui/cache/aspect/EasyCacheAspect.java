package com.easysui.cache.aspect;

import com.easysui.cache.annotation.EasyCacheExpire;
import com.easysui.cache.annotation.EasyCachePut;
import com.easysui.core.util.AspectUtil;
import com.easysui.redis.service.RedisService;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author CHAO 2019/4/16
 */
@Slf4j
@Aspect
public class EasyCacheAspect {
    @Autowired
    private RedisService redisService;

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
        //缓存key
        String contactKey = AspectUtil.contactValue(joinPoint, annotation.key());
        Class<?> cacheValue = redisService.get(contactKey, AspectUtil.getReturnType(joinPoint));
        if (Objects.nonNull(cacheValue)) {
            return cacheValue;
        }
        CacheInfo cacheInfo = buildCacheInfo(annotation, contactKey);
        return this.doCachePutProceed(joinPoint, cacheInfo);
    }

    @Around("cacheExpirePointCut()")
    public Object cacheExpireProcess(ProceedingJoinPoint joinPoint) throws Throwable {
        //注解
        EasyCacheExpire annotation = AspectUtil.getMethod(joinPoint).getAnnotation(EasyCacheExpire.class);
        //缓存key
        String cacheKey = AspectUtil.contactValue(joinPoint, annotation.key());
        redisService.del(this.buildKey(annotation.cacheName(), cacheKey));
        return joinPoint.proceed();
    }

    private Object doCachePutProceed(ProceedingJoinPoint joinPoint, CacheInfo cacheInfo) throws Throwable {
        Object result = joinPoint.proceed();
        Serializable cacheValue = result instanceof String ? StringUtils.defaultString(String.valueOf(result)) : (Serializable) result;
        String key = this.buildKey(cacheInfo.getCacheName(), cacheInfo.getCacheKey());
        redisService.set(key, cacheValue, cacheInfo.expireSeconds);
        return result;
    }

    private String buildKey(String cacheName, String cacheKey) {
        return String.format("%s_%s", cacheName, cacheKey);
    }

    private CacheInfo buildCacheInfo(EasyCachePut annotation, String cacheKey) {
        return CacheInfo.builder().cacheName(annotation.cacheName()).cacheKey(cacheKey).expireSeconds(annotation.expireSeconds()).build();
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
