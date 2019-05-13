package com.easysui.web.util;

import com.easysui.common.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

/**
 * @author CHAO 2019/4/17
 */
@Slf4j
public class ValidateUtil {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    public static <T> Result<String> validate(List<T> objects) {
        for (T obj : objects) {
            Set<ConstraintViolation<T>> validateSet = VALIDATOR.validate(obj);
            if (CollectionUtils.isNotEmpty(validateSet)) {
                StringBuilder builder = new StringBuilder();
                for (ConstraintViolation<T> v : validateSet) {
                    log.info("前置参数校验 - 校验到异常参数 {}: {}", v.getPropertyPath().toString(), v.getMessage());
                    builder.append(v.getMessage()).append("|");
                }
                return Result.error(builder.toString(),StringUtils.EMPTY);
            }
        }
        return Result.ok(StringUtils.EMPTY);
    }

}
