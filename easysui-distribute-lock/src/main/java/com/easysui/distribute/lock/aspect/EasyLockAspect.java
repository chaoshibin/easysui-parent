package com.easysui.distribute.lock.aspect;

import com.easysui.core.enums.ResultEnum;
import com.easysui.core.util.AspectUtil;
import com.easysui.core.util.CodecUtil;
import com.easysui.core.util.StringFormatUtils;
import com.easysui.distribute.lock.annotation.EasyLock;
import com.easysui.distribute.lock.enums.LockEnum;
import com.easysui.distribute.lock.service.DistributeLockService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

/**
 * @author Chao Shibin 2019/4/16
 */
@Slf4j
@Aspect
@Order(-1)
public class EasyLockAspect {
    private static final String LOCK_ERROR_MSG_FORMAT = ResultEnum.DISTRIBUTE_LOCK_FAIL.getMsg() + ",lockKey=%s";
    private Map<LockEnum, DistributeLockService> lockServiceMap = Maps.newHashMap();

    @Resource
    public void setLockService(DistributeLockService service) {
        lockServiceMap.put(service.type(), service);
    }

    @Pointcut("@annotation(com.easysui.distribute.lock.annotation.EasyLock)")
    public void pointCut() {
        //pointcut
    }

    @Around("pointCut()")
    public Object methodProcess(ProceedingJoinPoint joinPoint) throws Throwable {
        EasyLock annotation = AspectUtil.getAnnotationOnMethod(joinPoint, EasyLock.class);
        String lockKey = buildLockKey(joinPoint, annotation);
        String requestId = CodecUtil.createUuid();
        DistributeLockService lockService = lockServiceMap.get(annotation.type());
        if (Objects.isNull(lockService)) {
            Class<?> returnType = AspectUtil.getReturnType(joinPoint);
            return AspectUtil.buildResult(returnType, annotation.codeField(), annotation.msgField(),
                    ResultEnum.DISTRIBUTE_LOCK_SERVICE_INVALID.getCode(),
                    "分布式锁服务不可用，type=" + annotation.type().name());
        }
        if (!lockService.lock(lockKey, requestId, annotation.expireSeconds())) {
            //返回类型
            String msg = String.format(LOCK_ERROR_MSG_FORMAT, lockKey);
            log.info(msg);
            Class<?> returnType = AspectUtil.getReturnType(joinPoint);
            //争夺锁失败，构建失败对象
            return AspectUtil.buildResult(returnType, annotation.codeField(), annotation.msgField(), annotation.code(), msg);
        }
        Object result = joinPoint.proceed();
        lockService.unLock(lockKey, requestId);
        return result;
    }

    private String buildLockKey(JoinPoint joinPoint, EasyLock annotation) {
        String contactKey = AspectUtil.contactValue(joinPoint, annotation.key());
        return StringFormatUtils.format(annotation.name(), contactKey);
    }
}
