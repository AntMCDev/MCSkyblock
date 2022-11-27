package com.ant.mcskyblock.common.utils;

import java.io.*;

/**
 * [COMMON] HELPER CLASS - This class is used to serialize and deserialize classes to and from byte arrays.
 */
public class SerializationUtils {
    public static  <T extends Serializable> byte[] serialize(T obj) {
        byte[] result = null;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(obj);
            out.flush();
            result = bos.toByteArray();
        } catch (IOException ignore) {}
        return result;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T deserialize(byte[] bytes, Class<T> clazz) {
        T result = null;
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes)) {
            ObjectInputStream in = new ObjectInputStream(bis);
            result = (T)in.readObject();
        } catch (ClassNotFoundException | IOException ignore) {}
        return result;
    }
}
