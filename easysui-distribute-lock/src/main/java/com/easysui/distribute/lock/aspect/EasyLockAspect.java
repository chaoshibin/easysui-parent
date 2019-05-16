package com.easysui.distribute.lock.aspect;

import com.easysui.common.enums.ResultEnum;
import com.easysui.common.util.AspectUtil;
import com.easysui.common.util.CodecUtil;
import com.easysui.distribute.lock.annotation.EasyLock;
import com.easysui.distribute.lock.service.DistributeLockService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;

import javax.annotation.Resource;

/**
 * @author CHAO 2019/4/16
 */
@Slf4j
@Aspect
@Order(-1)
public class EasyLockAspect {
    private final static String LOCK_ERROR_MSG_FORMAT = ResultEnum.DISTRIBUTE_LOCK_FAIL.getMsg() + ",lockKey=%s";
    @Resource
    private DistributeLockService distributeLockService;

    @Pointcut("@annotation(com.easysui.distribute.lock.annotation.EasyLock)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object methodProcess(ProceedingJoinPoint joinPoint) throws Throwable {
        EasyLock annotation = AspectUtil.getAnnotationOnMethod(joinPoint, EasyLock.class);
        String lockKey = buildLockKey(joinPoint, annotation);
        String requestId = CodecUtil.createUUID();
        if (!distributeLockService.lock(lockKey, requestId, annotation.expireSeconds() * 1000)) {
            //返回类型
            String msg = String.format(LOCK_ERROR_MSG_FORMAT, lockKey);
            log.info(msg);
            Class<?> returnType = AspectUtil.getReturnType(joinPoint);
            //争夺锁失败，构建失败对象
            return AspectUtil.buildResult(returnType, annotation.codeField(), annotation.msgField(), annotation.code(), msg);
        }
        Object result = joinPoint.proceed();
        distributeLockService.unLock(lockKey, requestId);
        return result;
    }

    private String buildLockKey(JoinPoint joinPoint, EasyLock annotation) {
        String contactKey = AspectUtil.contactValue(joinPoint, annotation.key());
        return String.format("%s_%s", annotation.name(), contactKey);
    }
}
