package com.easysui.common.util;

import com.google.common.base.MoreObjects;
import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;

/**
 * 功能描述:
 * <p/>
 *
 * @author CHAO 新增日期：2018/9/19
 * @author CHAO 修改日期：2018/9/19
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class StringSerializable implements Serializable {


    @Override
    protected Object clone() {
        return SerializationUtils.clone(this);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).toString();
    }

}
