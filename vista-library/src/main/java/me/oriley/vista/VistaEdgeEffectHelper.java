package me.oriley.vista;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.CheckResult;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.EdgeEffectCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import java.lang.reflect.Field;
import java.util.HashMap;

@SuppressWarnings("WeakerAccess")
public final class VistaEdgeEffectHelper {

    public enum Side {
        LEFT, RIGHT, TOP, BOTTOM
    }

    private static final String TAG = VistaEdgeEffectHelper.class.getSimpleName();
    private static final String COMPAT_EDGE_EFFECT = "mEdgeEffect";
    private static final int INVALID = -1;

    @NonNull
    private final Class<? extends View> mViewClass;

    @NonNull
    private final VistaEdgeEffectHost mHost;

    @NonNull
    private final HashMap<Side, VistaEdgeEffect> mEdges = new HashMap<>();

    private final int mInitialGlowColour;


    public VistaEdgeEffectHelper(@NonNull Class<? extends View> viewClass,
                                 @NonNull VistaEdgeEffectHost customEdgeEffectHost,
                                 @NonNull Context context,
                                 @Nullable AttributeSet attrs) {
        mViewClass = viewClass;
        mHost = customEdgeEffectHost;
        mInitialGlowColour = readColorAttribute(context, attrs);
    }


    @ColorInt
    private int readColorAttribute(@NonNull Context context, @Nullable AttributeSet attrs) {
        int initialColor = INVALID;
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.VistaView);
            if (a.hasValue(R.styleable.VistaView_vistaEdgeEffectColor)) {
                initialColor = a.getColor(R.styleable.VistaView_vistaEdgeEffectColor, INVALID);
            }
            a.recycle();
        }

        // Couldn't get from attribute, try app compat accent color
        if (initialColor == INVALID) {
            TypedValue typedValue = new TypedValue();
            TypedArray a = context.obtainStyledAttributes(typedValue.data, new int[] { R.attr.colorAccent });
            initialColor = a.getColor(0, 0);
            a.recycle();
        }

        return initialColor;
    }

    public void refreshEdges() {
        Context context = mHost.getContext();
        mEdges.clear();
        for (VistaEdgeEffectModel model : mHost.getEdgeEffectModels()) {
            VistaEdgeEffect edgeEffect = new VistaEdgeEffect(context);
            edgeEffect.setColor(mInitialGlowColour);
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

            edgeEffectCompat.setSize(mHost.getMeasuredWidth(), mHost.getMeasuredHeight());
            Log.d(TAG, "Replaced edge effect " + fieldName + " in " + mViewClass + " for item " + mHost);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error replacing edge effect " + fieldName + " in " + mViewClass + " for item " + mHost);
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

            edgeEffect.setSize(mHost.getMeasuredWidth(), mHost.getMeasuredHeight());
            Log.d(TAG, "Replaced edge effect " + fieldName + " in " + mViewClass + " for item " + mHost);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error replacing edge effect " + fieldName + " in " + mViewClass + " for item " + mHost);
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
