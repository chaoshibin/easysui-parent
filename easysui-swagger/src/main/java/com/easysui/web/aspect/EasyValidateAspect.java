package com.easysui.web.aspect;

import com.easysui.common.util.AspectUtil;
import com.easysui.common.util.Result;
import com.easysui.web.annotation.EasyValidate;
import com.easysui.web.util.ValidateUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author CHAO 2019/4/16
 */
@Slf4j
@Aspect
@Order(2)
public class EasyValidateAspect {

    @Pointcut("@annotation(com.easysui.web.annotation.EasyValidate)")
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
            //返回码属性域
            String codeField = easyValidateAnnotation.codeField();
            //返回信息属性域
            String msgField = easyValidateAnnotation.msgField();
            //获取验证失败错误码
            String code = easyValidateAnnotation.code();
            return AspectUtil.buildResult(returnType, codeField, msgField, code, result.getMsg());
        }
        return joinPoint.proceed();
    }
}
