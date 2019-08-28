package com.easysui.core.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * 编码与解码操作工具类
 *
 * @author CHAO
 */
@Slf4j
public final class CodecUtil {

    public static final String UTF_8 = "UTF-8";

    private CodecUtil() {
    }

    /**
     * 将 URL 编码
     */
    public static String encodeUrl(String str) {
        String target;
        try {
            target = URLEncoder.encode(str, UTF_8);
        } catch (Exception e) {
            log.error("encode URL failure", e);
            throw new RuntimeException(e);
        }
        return target;
    }

    /**
     * 将 URL 解码
     */
    public static String decodeUrl(String str) {
        String target;
        try {
            target = URLDecoder.decode(str, UTF_8);
        } catch (Exception e) {
            log.error("decode URL failure", e);
            throw new RuntimeException(e);
        }
        return target;
    }

    /**
     * 将字符串 Base64 编码
     */
    public static String encodeBase64(String str) {
        String target;
        try {
            target = Base64.encodeBase64URLSafeString(str.getBytes(UTF_8));
        } catch (UnsupportedEncodingException e) {
            log.error("encode BASE64 failure", e);
            throw new RuntimeException(e);
        }
        return target;
    }

    /**
     * 将字符串 Base64 解码
     */
    public static String decodeBase64(String str) {
        String target;
        try {
            target = new String(Base64.decodeBase64(str), UTF_8);
        } catch (UnsupportedEncodingException e) {
            log.error("decode BASE64 failure", e);
            throw new RuntimeException(e);
        }
        return target;
    }

    /**
     * 将字符串 MD5 加密
     */
    public static String encryptMd5(String str) {
        return DigestUtils.md5Hex(str);
    }

    /**
     * 将字符串 SHA1 加密
     *
     * @param str
     * @return 32位
     */
    public static String encryptSha1(String str) {
        return DigestUtils.sha1Hex(str);
    }


    /**
     * 将字符串 SHA256 加密
     *
     * @param str
     * @return 64位
     */
    public static String encryptSha256(String str) {
        return DigestUtils.sha256Hex(str);
    }

    /**
     * 创建随机数
     */
    public static String createRandom(int count) {
        return RandomStringUtils.randomNumeric(count);
    }

    /**
     * 获取 UUID（32位）
     */
    public static String createUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
