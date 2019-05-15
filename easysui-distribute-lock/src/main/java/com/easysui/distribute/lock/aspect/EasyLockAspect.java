package com.easysui.distribute.lock.aspect;

import com.easysui.common.enums.ResultEnum;
import com.easysui.common.util.AspectUtil;
import com.easysui.distribute.lock.annotation.EasyLock;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;

/**
 * @author CHAO 2019/4/16
 */
@Slf4j
@Aspect
@Order(2)
public class EasyLockAspect {
    private final static String LOCK_ERROR_MSG_FORMAT = ResultEnum.DISTRIBUTE_LOCK_FAIL.getMsg() + ",lockKey=%s";

    @Pointcut("@annotation(com.easysui.distribute.lock.annotation.EasyLock)")
    public void pointCut() {

    }

    @Around("pointCut()")
    public Object methodProcess(ProceedingJoinPoint joinPoint) throws Throwable {
        EasyLock annotation = AspectUtil.getAnnotationOnMethod(joinPoint, EasyLock.class);
        String lockKey = buildLockKey(annotation);
        //TODO 加锁
        boolean lock = false;
        //争夺锁失败，构建失败对象
        if (!lock) {
            //返回类型
            String msg = String.format(LOCK_ERROR_MSG_FORMAT, annotation.key());
            log.info(msg);
            Class<?> returnType = AspectUtil.getReturnType(joinPoint);
            return AspectUtil.buildResult(returnType, annotation.codeField(), annotation.msgField(), annotation.code(), msg);
        }
        Object result = joinPoint.proceed();
        //TODO 解锁
        return result;
    }

    private String buildLockKey(EasyLock annotation) {
        return String.format("%s_%s", annotation.prefix(), annotation.key());
    }
}
