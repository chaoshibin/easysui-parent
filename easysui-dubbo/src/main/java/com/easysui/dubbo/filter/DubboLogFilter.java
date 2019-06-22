
package com.easysui.dubbo.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.easysui.core.constant.ConstantPool;
import com.easysui.core.util.JsonUtil;
import com.easysui.log.annotation.EasyLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import java.lang.reflect.Method;
import java.util.Objects;

@Activate(group = {Constants.PROVIDER, Constants.CONSUMER})
@Slf4j
@SuppressWarnings("unchecked")
public class DubboLogFilter implements Filter {
    @Override
    public Result invoke(final Invoker<?> invoker, final Invocation invocation) throws RpcException {
        RpcContext.getContext().setAttachment(ConstantPool.REQUEST_ID, MDC.get(ConstantPool.REQUEST_ID));
        RpcContext.getContext().setAttachment(ConstantPool.SERVER_IP, MDC.get(ConstantPool.SERVER_IP));
        String methodName = invocation.getMethodName();
        Class clazz = invoker.getInterface();
        Class[] args = invocation.getParameterTypes();
        final Object[] arguments = invocation.getArguments();
        Method method;
        EasyLog easyLog = null;

        converterParamsClass(args, arguments);
        try {
            method = clazz.getMethod(methodName, args);
            easyLog = method.getAnnotation(EasyLog.class);
        } catch (Exception e) {
            log.error("获取注解方法异常", e);
        }
        String text = StringUtils.left(JsonUtil.toJSON(arguments), 2000);
        if (Objects.isNull(easyLog)) {
            Result result = null;
            try {
                log.info("RpcMethod={}, 请求报文={}", methodName, text);
                result = invoker.invoke(invocation);
                log.info("RpcMethod={}, 请求报文={}, 响应报文={}", methodName, text, JsonUtil.toJSON(result.getValue()));
                return result;
            } catch (RpcException e) {
                log.error("异常 RpcMethod={}, 请求报文={}", methodName, text);
                throw e;
            }
        }
        try {
            log.info("[{}] RpcMethod={}, 请求报文={}", easyLog.title(), methodName, text);
            Result result = invoker.invoke(invocation);
            log.info("[{}] RpcMethod={}, 请求报文={}, 响应报文={}", easyLog.title(), methodName, text, JsonUtil.toJSON(result.getValue()));
            return result;
        } catch (Throwable e) {
            log.error("[{}异常] RpcMethod={}, 请求报文={}", easyLog.title(), methodName, text, e);
            throw e;
        }
    }

    private void converterParamsClass(final Class[] args, final Object[] arguments) {
        if (arguments == null || arguments.length < 1) {
            return;
        }
        for (int i = 0; i < arguments.length; i++) {
            if (arguments[i] != null) {
                args[i] = arguments[i].getClass();
            }
        }
    }
}
