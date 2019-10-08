package com.easysui.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.List;


/**
 * @author Chao Shibin
 */
@Slf4j
@ConditionalOnProperty(prefix = "easysui.http", name = "enabled", havingValue = "true")
public class RestTemplateConfiguration {

    @Bean
    @ConditionalOnMissingBean({RestOperations.class, RestTemplate.class})
    public RestTemplate restTemplate(ClientHttpRequestFactory httpRequestFactory) {
        log.info("easysui初始化RestTemplate");
        RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
        //StringHttpMessageConverter 默认使用ISO-8859-1编码，此处修改为UTF-8
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        messageConverters.forEach(converter -> {
            if (converter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) converter).setDefaultCharset(Charset.forName("UTF-8"));
            }
        });
        return restTemplate;
    }

    @Bean
    @ConditionalOnMissingBean({ClientHttpRequestFactory.class})
    public ClientHttpRequestFactory httpRequestFactory(HttpClient httpClient) {
        /*
         * Spring使用；两种方式实现http请求
         * 1.SimpleClientHttpRequestFactory，使用J2SE提供的方式（既java.net包提供的方式）创建底层的Http请求连接
         * 2.HttpComponentsClientHttpRequestFactory，底层使用HttpClient访问远程的Http服务，使用HttpClient可以配置连接池和证书等信息
         */
        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }

    @Bean
    @ConditionalOnMissingBean({HttpClient.class})
    public HttpClient httpClient(RestTemplateProperties properties) {
        log.info("easysui初始化HttpClient，配置参数->{}", properties);
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(properties.getMaxTotal());
        connectionManager.setDefaultMaxPerRoute(properties.getDefaultMaxPerRoute());
        RequestConfig requestConfig = RequestConfig.custom()
                //读取超时时间
                .setSocketTimeout(properties.getSocketTimeoutMs())
                //连接超时时间
                .setConnectTimeout(properties.getConnectTimeoutMs())
                .setConnectionRequestTimeout(properties.getConnectionRequestTimeoutMs())
                .build();
        return HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(connectionManager)
                //设置重试次数，默认三次未开启
                //.setRetryHandler(new DefaultHttpRequestRetryHandler(2, true))
                .build();
    }
}
