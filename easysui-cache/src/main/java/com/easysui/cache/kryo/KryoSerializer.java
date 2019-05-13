package com.easysui.cache.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

/**
 * @author CHAO 2019/5/14 1:58
 */
public class KryoSerializer {
    private final static ThreadLocal<Kryo> KRYO_THREAD_LOCAL = ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();
        kryo.setReferences(true);
        kryo.setRegistrationRequired(false);
        return kryo;
    });

    public byte[] serialize(Object obj) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (Output output = new Output(outputStream)) {
            KRYO_THREAD_LOCAL.get().writeClassAndObject(output, obj);
        }
        return outputStream.toByteArray();
    }

    public <T> T deserialize(byte[] bytes) {
        ByteArrayInputStream outputStream = new ByteArrayInputStream(bytes);
        try (Input input = new Input(outputStream)) {
           return (T) KRYO_THREAD_LOCAL.get().readClassAndObject(input);
        }
    }
}
