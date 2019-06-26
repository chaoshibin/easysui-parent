
package com.easysui.dubbo.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.easysui.core.constant.ConstantPool;
import com.easysui.core.util.CodecUtil;
import com.easysui.core.util.JsonUtil;
import com.easysui.log.annotation.EasyLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author CHAO
 */
@Activate(group = {Constants.PROVIDER, Constants.CONSUMER})
@Slf4j
@SuppressWarnings("unchecked")
public class LogFilter implements Filter {
    @Override
    public Result invoke(final Invoker<?> invoker, final Invocation invocation) throws RpcException {
        //用于日志打印
        if (StringUtils.isNotEmpty(MDC.get(ConstantPool.REQUEST_ID))) {
            RpcContext.getContext().setAttachment(ConstantPool.REQUEST_ID, MDC.get(ConstantPool.REQUEST_ID));
        }
        if (StringUtils.isNotEmpty(MDC.get(ConstantPool.SERVER_IP))) {
            RpcContext.getContext().setAttachment(ConstantPool.SERVER_IP, MDC.get(ConstantPool.SERVER_IP));
        }
        if (StringUtils.isEmpty(MDC.get(ConstantPool.REQUEST_ID)) &&
                StringUtils.isNotEmpty(RpcContext.getContext().getAttachment(ConstantPool.REQUEST_ID))) {
            MDC.put(ConstantPool.REQUEST_ID, RpcContext.getContext().getAttachment(ConstantPool.REQUEST_ID));
        }
        if (StringUtils.isEmpty(MDC.get(ConstantPool.SERVER_IP)) &&
                StringUtils.isNotEmpty(RpcContext.getContext().getAttachment(ConstantPool.SERVER_IP))) {
            MDC.put(ConstantPool.SERVER_IP, RpcContext.getContext().getAttachment(ConstantPool.SERVER_IP));
        }
        if (StringUtils.isEmpty(MDC.get(ConstantPool.REQUEST_ID)) &&
                StringUtils.isEmpty(RpcContext.getContext().getAttachment(ConstantPool.REQUEST_ID))) {
            String uuid = CodecUtil.createUUID();
            MDC.put(ConstantPool.REQUEST_ID, uuid);
            RpcContext.getContext().setAttachment(ConstantPool.REQUEST_ID, uuid);
        }
        if (StringUtils.isEmpty(MDC.get(ConstantPool.SERVER_IP)) &&
                StringUtils.isEmpty(RpcContext.getContext().getAttachment(ConstantPool.SERVER_IP))) {
            String uuid = CodecUtil.createUUID();
            MDC.put(ConstantPool.SERVER_IP, uuid);
            RpcContext.getContext().setAttachment(ConstantPool.SERVER_IP, uuid);
        }
        Class clazz = invoker.getInterface();
        Class[] args = invocation.getParameterTypes();
        final Object[] arguments = invocation.getArguments();
        EasyLog easyLog = null;
        converterParamsClass(args, arguments);
        String methodName = StringUtils.EMPTY;
        try {
            Method method = clazz.getMethod(invocation.getMethodName(), args);
            easyLog = AnnotationUtils.findAnnotation(method, EasyLog.class);
            methodName = clazz.getName() + "." + method.getName();
        } catch (Exception e) {
            log.error("获取注解方法异常", e);
        }
        String text = StringUtils.left(JsonUtil.toJSON(arguments), 2000);
        if (Objects.isNull(easyLog)) {
            log.info("RpcMethod={}, 请求报文={}", methodName, text);
            Result result = invoker.invoke(invocation);
            log.info("RpcMethod={}, 请求报文={}, 响应报文={}", methodName, text, JsonUtil.toJSON(result.getValue()));
            if (result.hasException()) {
                log.error("[###异常###] RpcMethod={}, 请求报文={}", methodName, text, result.getException());
            }
            return result;
        }
        log.info("[{}] RpcMethod={}, 请求报文={}", easyLog.title(), methodName, text);
        Result result = invoker.invoke(invocation);
        log.info("[{}] RpcMethod={}, 请求报文={}, 响应报文={}", easyLog.title(), methodName, text, JsonUtil.toJSON(result.getValue()));
        if (result.hasException()) {
            log.error("[{}###异常###] RpcMethod={}, 请求报文={}", easyLog.title(), methodName, text, result.getException());
        }
        return result;
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
