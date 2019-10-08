
package com.easysui.dubbo.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.easysui.log.annotation.EasyLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author Chao Shibin
 */
@Slf4j
@Activate(group = {Constants.PROVIDER, Constants.CONSUMER})
public class LogFilter implements Filter {
    @Override
    public Result invoke(final Invoker<?> invoker, final Invocation invocation) {
        Class clazz = invoker.getInterface();
        Class[] args = invocation.getParameterTypes();
        final Object[] arguments = invocation.getArguments();
        EasyLog easyLog = null;
        String methodName = StringUtils.EMPTY;
        try {
            @SuppressWarnings("unchecked")
            Method method = clazz.getMethod(invocation.getMethodName(), args);
            easyLog = AnnotationUtils.findAnnotation(method, EasyLog.class);
            methodName = clazz.getName() + "." + method.getName();
        } catch (Exception e) {
            log.error("获取注解方法异常", e);
        }
        //没有注解不生效
        if (Objects.isNull(easyLog)) {
            return invoker.invoke(invocation);
        }
        String text = StringUtils.left(ArrayUtils.toString(arguments), 2000);
        StopWatch stopWatch = StopWatch.createStarted();
        log.info("[{}] RpcMethod={}, 请求报文={}", easyLog.title(), methodName, text);
        Result result = invoker.invoke(invocation);
        log.info("[{}] RpcMethod={}, 请求报文={}, 响应报文={}, 耗时{}", easyLog.title(), methodName, text, result.getValue(), stopWatch);
        if (result.hasException()) {
            log.error("[{}###异常###] RpcMethod={}, 请求报文={}, 耗时{}", easyLog.title(), methodName, text, stopWatch, result.getException());
        }
        return result;
    }
}
