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

    @SuppressWarnings("unchecked")
    public static Result<String> validate(List<Object> objects) {
        for (Object obj : objects) {
            Set<ConstraintViolation<Object>> validateSet = VALIDATOR.validate(obj);
            if (CollectionUtils.isNotEmpty(validateSet)) {
                StringBuilder builder = new StringBuilder();
                for (ConstraintViolation<Object> v : validateSet) {
                    log.info("前置参数校验-校验到异常参数 {}={}", v.getPropertyPath().toString(), v.getMessage());
                    if (builder.length() > Integer.SIZE) {
                        builder.append("|");
                    }
                    builder.append(v.getMessage());
                }
                return Result.error(builder.toString(), StringUtils.EMPTY);
            }
        }
        return Result.ok(StringUtils.EMPTY);
    }

}
