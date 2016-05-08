package me.oriley.vista;

import android.content.Context;
import android.support.annotation.CheckResult;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.widget.EdgeEffectCompat;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;
import java.util.HashMap;

public final class VistaEdgeEffectHelper {

    public enum Side {
        LEFT, RIGHT, TOP, BOTTOM
    }

    private static final String TAG = VistaEdgeEffectHelper.class.getSimpleName();

    private static final String COMPAT_EDGE_EFFECT = "mEdgeEffect";

    @NonNull
    private final Class<? extends View> mViewClass;

    @NonNull
    private final VistaEdgeEffectHost mHost;

    @NonNull
    private final HashMap<Side, VistaEdgeEffect> mEdges = new HashMap<>();


    public VistaEdgeEffectHelper(@NonNull Class<? extends View> viewClass,
                                 @NonNull VistaEdgeEffectHost customEdgeEffectHost,
                                 @NonNull Context context) {
        mViewClass = viewClass;
        mHost = customEdgeEffectHost;

        for (VistaEdgeEffectModel model : mHost.getEdgeEffectModels()) {
            VistaEdgeEffect edgeEffect = new VistaEdgeEffect(context);
            if (replaceEdgeEffect(context, model.fieldName, edgeEffect, model.isCompat)) {
                mEdges.put(model.side, edgeEffect);
            }
        }
    }


    @CheckResult
    private boolean replaceEdgeEffect(@NonNull Context context,
                                      @NonNull String fieldName,
                                      @NonNull VistaEdgeEffect edgeEffect,
                                      boolean isCompat) {
        if (isCompat) {
            return replaceEdgeEffectCompat(context, fieldName, edgeEffect);
        } else {
            return replaceEdgeEffect(fieldName, edgeEffect);
        }
    }

    @CheckResult
    private boolean replaceEdgeEffectCompat(@NonNull Context context,
                                            @NonNull String fieldName,
                                            @NonNull VistaEdgeEffect edgeEffect) {
        try {
            Field edgeEffectCompatField = mViewClass.getDeclaredField(fieldName);
            edgeEffectCompatField.setAccessible(true);

            EdgeEffectCompat edgeEffectCompat = new EdgeEffectCompat(context);
            Field edgeEffectField = EdgeEffectCompat.class.getDeclaredField(COMPAT_EDGE_EFFECT);
            edgeEffectField.setAccessible(true);
            edgeEffectField.set(edgeEffectCompat, edgeEffect);

            edgeEffectCompatField.set(mHost, edgeEffectCompat);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error replacing edge effect " + fieldName + " in class " + mViewClass);
            e.printStackTrace();
            return false;
        }
    }

    @CheckResult
    private boolean replaceEdgeEffect(@NonNull String fieldName,
                                      @NonNull VistaEdgeEffect edgeEffect) {
        try {
            Field field = mViewClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(mHost, edgeEffect);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error replacing edge effect " + fieldName + " in class " + mViewClass);
            e.printStackTrace();
            return false;
        }
    }

    public void setEdgeEffectColors(@ColorInt int color) {
        for (VistaEdgeEffect edgeEffect : mEdges.values()) {
            if (edgeEffect != null) {
                edgeEffect.setColor(color);
            }
        }
    }

    public void setEdgeEffectColor(@NonNull Side side, @ColorInt int color) {
        VistaEdgeEffect edgeEffect = mEdges.get(side);
        if (edgeEffect != null) {
            edgeEffect.setColor(color);
        }
    }
}
