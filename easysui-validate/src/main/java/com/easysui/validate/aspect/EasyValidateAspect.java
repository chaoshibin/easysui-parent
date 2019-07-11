package com.easysui.validate.aspect;

import com.easysui.core.util.AspectUtil;
import com.easysui.core.util.Result;
import com.easysui.validate.util.ValidateUtil;
import com.easysui.validate.annotation.EasyValidate;
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
@Order(-2147483646)
public class EasyValidateAspect {

    @Pointcut("@annotation(com.easysui.validate.annotation.EasyValidate)")
    public void pointCut() {

    }

    @Around("pointCut()")
    public Object methodProcess(ProceedingJoinPoint joinPoint) throws Throwable {
        Result<String> result = ValidateUtil.validate(Arrays.asList(joinPoint.getArgs()));
        //验证失败
        if (result.isError()) {
            //返回类型
            Class<?> returnType = AspectUtil.getReturnType(joinPoint);
            EasyValidate easyValidateAnnotation = AspectUtil.getMethod(joinPoint).getAnnotation(EasyValidate.class);
            //错误码域
            String codeField = easyValidateAnnotation.codeField();
            //错误信息域
            String msgField = easyValidateAnnotation.msgField();
            //验证失败错误码
            String code = easyValidateAnnotation.code();
            return AspectUtil.buildResult(returnType, codeField, msgField, code, result.getMsg());
        }
        return joinPoint.proceed();
    }
}
