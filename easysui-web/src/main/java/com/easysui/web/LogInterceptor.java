package com.easysui.web;

import com.easysui.core.util.CodecUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;

/**
 * @author CHAO
 * 2019年1月28日15:16:05
 */
@Slf4j
public class LogInterceptor implements HandlerInterceptor {
    private final static String REQUEST_ID = "requestId";
    private final static String SERVER_IP = "serverIp";

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String requestId = CodecUtil.createUUID();
        log.info("requestId:{}, clientIp:{}, X-Forwarded-For:{}", requestId, httpServletRequest.getRemoteAddr(),
                StringUtils.defaultString(httpServletRequest.getHeader("X-Forwarded-For")));
        MDC.put(REQUEST_ID, requestId);
        MDC.put(SERVER_IP, InetAddress.getLocalHost().getHostAddress());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) {
        MDC.remove(REQUEST_ID);
        MDC.remove(SERVER_IP);
    }
}
