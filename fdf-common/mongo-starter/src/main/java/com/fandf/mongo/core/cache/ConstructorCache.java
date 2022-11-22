
package com.fandf.mongo.core.cache;

import com.fandf.mongo.core.exception.ConstructorException;

import java.lang.ref.SoftReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@SuppressWarnings("unchecked")
public class ConstructorCache {

    private final ConcurrentMap<String, SoftReference<Constructor<?>>> cache = new ConcurrentHashMap<>();

    private ConstructorCache() {

    }

    private static class Holder {
        final static ConstructorCache instance = new ConstructorCache();
    }

    public static ConstructorCache getInstance() {
        return Holder.instance;
    }

    private <T> Constructor<T> get(Class<T> clazz) {
        String name = clazz.getName();
        Constructor<T> cons = null;
        boolean recycled = false;
        SoftReference<Constructor<?>> sr = cache.get(name);
        if (sr != null) {
            cons = (Constructor<T>) sr.get();
            if (cons == null) {
                recycled = true;
            } else {
                return cons;
            }
        }
        //if not exists
        Class<T>[] types = null;
        try {
            cons = clazz.getConstructor(types);
        } catch (NoSuchMethodException | SecurityException ex) {
            throw new ConstructorException(ex.getMessage());
        }
        sr = new SoftReference<>(cons);
        if (recycled) {
            cache.put(name, sr);
            return cons;
        } else {
            SoftReference<Constructor<?>> temp = cache.putIfAbsent(name, sr);
            if (temp != null) {
                return (Constructor<T>) temp.get();
            } else {
                return (Constructor<T>) sr.get();
            }
        }
    }

    public <T> T create(Class<T> clazz) {
        T obj = null;
        Constructor<T> cons = get(clazz);
        Object[] args = null;
        try {
            obj = cons.newInstance(args);
        } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | InvocationTargetException ex) {
            throw new ConstructorException(ex.getMessage());
        }
        return obj;
    }

}
