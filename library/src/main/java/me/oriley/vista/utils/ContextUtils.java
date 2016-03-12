package me.oriley.vista.utils;

import android.app.Application;
import android.support.annotation.Nullable;

import java.lang.reflect.Method;

import static me.oriley.vista.utils.ReflectionUtils.getClassForName;
import static me.oriley.vista.utils.ReflectionUtils.getFieldValue;
import static me.oriley.vista.utils.ReflectionUtils.getMethod;
import static me.oriley.vista.utils.ReflectionUtils.tryInvoke;

public final class ContextUtils {

    private static final String TAG = ReflectionUtils.class.getSimpleName();

    @Nullable
    private static Method CURRENT_APPLICATION;

    static {
        Class activityThread = getClassForName("android.app.ActivityThread");
        CURRENT_APPLICATION = activityThread != null ?
                getMethod(activityThread, "currentApplication") : null;
    }

    private ContextUtils() {
        throw new IllegalAccessError("no instances");
    }

    @Nullable
    public static Application currentApplication() {
        return tryInvoke(CURRENT_APPLICATION);
    }
}
