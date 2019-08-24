package com.easysui.validation.aspect;

import com.easysui.core.util.AspectUtil;
import com.easysui.core.util.Result;
import com.easysui.validation.annotation.EasyValidation;
import com.easysui.validation.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;

import java.util.Arrays;

/**
 * @author CHAO 2019/4/16
 */
@Slf4j
@Aspect
@Order(Integer.MIN_VALUE + 1)
public class EasyValidationAspect {

    @Pointcut("@annotation(com.easysui.validation.annotation.EasyValidation)")
    public void pointCut() {

    }

    @Around("pointCut()")
    public Object methodProcess(ProceedingJoinPoint joinPoint) throws Throwable {
        Result<String> result = ValidationUtil.validate(Arrays.asList(joinPoint.getArgs()));
        //验证失败
        if (result.isError()) {
            //返回类型
            Class<?> returnType = AspectUtil.getReturnType(joinPoint);
            EasyValidation easyValidationAnnotation = AspectUtil.getMethod(joinPoint).getAnnotation(EasyValidation.class);
            //错误码域
            String codeField = easyValidationAnnotation.codeField();
            //错误信息域
            String msgField = easyValidationAnnotation.msgField();
            //验证失败错误码
            String code = easyValidationAnnotation.code();
            return AspectUtil.buildResult(returnType, codeField, msgField, code, result.getMsg());
        }
        return joinPoint.proceed();
    }
}
