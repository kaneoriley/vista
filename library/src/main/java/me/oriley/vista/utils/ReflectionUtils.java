package me.oriley.vista.utils;

import android.support.annotation.Nullable;

import android.util.Log;
import lombok.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@SuppressWarnings("unused")
public final class ReflectionUtils {

    private static final String TAG = ReflectionUtils.class.getSimpleName();

    private ReflectionUtils() {
        throw new IllegalAccessError("no instances");
    }

    @Nullable
    public static Class getClassForName(@NonNull String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "class " + className + " not found");
            return null;
        }
    }

    @Nullable
    public static Field getField(@NonNull Class<?> clazz, @NonNull String name) {
        try {
            Field field = clazz.getDeclaredField(name);
            if (field == null) {
                return null;
            }
            field.setAccessible(true);
            return field;
        } catch (Throwable e) {
            Log.e(TAG, "Failed to get field " + name + " from class " + clazz.getCanonicalName(), e);
            return null;
        }
    }

    @Nullable
    public static Method getMethod(@NonNull Class<?> clazz, @NonNull String name, @Nullable Class<?>... params) {
        try {
            Method method = clazz.getDeclaredMethod(name, params);
            if (method == null) {
                return null;
            }
            method.setAccessible(true);
            return method;
        } catch (Throwable e) {
            Log.e(TAG, "Failed to get method " + name + " from class " + clazz.getCanonicalName(), e);
            return null;
        }
    }

    @Nullable
    public static Object getValue(@Nullable Field field, @Nullable Object obj) {
        if (field == null) {
            return null;
        }
        if (obj == null) {
            return null;
        }
        try {
            return field.get(obj);
        } catch (Throwable e) {
            Log.e(TAG, "Failed to get value of " + obj.getClass().getCanonicalName() + "." + field.getName(), e);
            return null;
        }
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public static <T> T getFieldValue(@NonNull Class<?> clazz,
                                      @Nullable Object obj,
                                      @NonNull String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return (T) field.get(obj);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "error retrieving field " + fieldName + " from class " + clazz);
            return null;
        } catch (NoSuchFieldException e) {
            Log.e(TAG, "error retrieving field " + fieldName + " from class " + clazz);
            return null;
        }
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public static <T> T getFieldValue(@NonNull Object obj, @NonNull String fieldName) {
        return getFieldValue(obj.getClass(), obj, fieldName);
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public static <T> T tryInvoke(@Nullable Method method) {
        if (method == null) {
            return null;
        }

        try {
            return (T) method.invoke(null);
        } catch (InvocationTargetException e) {
            Log.e(TAG, "error invoking method " + method);
            return null;
        } catch (IllegalAccessException e) {
            Log.e(TAG, "error invoking method " + method);
            return null;
        }
    }
}