package com.easysui.cache.aspect;

import com.easysui.cache.annotation.EasyCacheExpire;
import com.easysui.cache.annotation.EasyCachePut;
import com.easysui.core.constant.StrConst;
import com.easysui.core.util.AspectUtil;
import com.easysui.core.util.StringFormatUtils;
import com.easysui.redis.service.RedisService;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author CHAO 2019/4/16
 */
@Slf4j
@Aspect
@Order
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
        CacheInfo cacheInfo = buildCachePutInfo(joinPoint);
        Object cacheValue = redisService.get(cacheInfo.getKey(), AspectUtil.getReturnType(joinPoint));
        if (Objects.nonNull(cacheValue)) {
            //1.1和1.2处协同解决缓存击穿     1.1
            return this.defaultIfNull(cacheValue);
        }
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
        if (Objects.nonNull(result) || cacheInfo.isCacheNull()) {
            // 1.1和1.2处协同解决缓存击穿      1.2
            Serializable value = (Serializable) this.putIfNull(result);
            redisService.set(cacheInfo.getKey(), value, cacheInfo.getExpireSeconds());
        }
        return result;
    }

    private Object putIfNull(Object value) {
        return Objects.isNull(value) ? StrConst.NULL : value;
    }

    private Object defaultIfNull(Object value) {
        return Objects.equals(StrConst.NULL, value) ? null : value;
    }

    private String buildKey(String cacheName, String cacheKey) {
        return StringFormatUtils.format(cacheName, cacheKey);
    }

    private CacheInfo buildCachePutInfo(ProceedingJoinPoint pjp) {
        EasyCachePut annotation = AspectUtil.getMethod(pjp).getAnnotation(EasyCachePut.class);
        long expireSeconds = annotation.expireSeconds();
        if (expireSeconds == 0L) {
            //随机数防止缓存雪崩 30-60分钟
            expireSeconds = RandomUtils.nextLong(1800, 3600);
        }
        //缓存key
        String contactKey = AspectUtil.contactValue(pjp, annotation.key());
        String key = this.buildKey(annotation.cacheName(), contactKey);
        return CacheInfo.builder().key(key).expireSeconds(expireSeconds).cacheNull(annotation.cacheNull()).build();
    }

    @Setter
    @Getter
    @Builder
    private static class CacheInfo {
        private String key;
        private long expireSeconds;
        private boolean cacheNull;
    }
}
