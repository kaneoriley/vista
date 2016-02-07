package me.oriley.vista.scrollviews;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.CheckResult;
import android.support.annotation.ColorInt;
import android.support.v4.widget.EdgeEffectCompat;
import android.util.Log;
import android.view.View;
import android.widget.EdgeEffect;

import java.lang.reflect.Field;
import java.util.HashMap;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@SuppressWarnings("unused")
@Accessors(prefix = "m")
public abstract class EdgeEffects {

    public enum Side {
        LEFT, RIGHT, TOP, BOTTOM
    }

    private static final String TAG = EdgeEffects.class.getSimpleName();

    private static final String COMPAT_EDGE_EFFECT = "mEdgeEffect";

    @NonNull
    public static EdgeEffects get(@NonNull Class<? extends View> viewClass,
                                  @NonNull CustomEdgeEffectHost customEdgeEffectHost,
                                  @NonNull Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return new EdgeEffectsLollipop(viewClass, customEdgeEffectHost, context);
        } else {
            return new EdgeEffectsHolo();
        }
    }

    private static final class CustomEdgeEffect extends EdgeEffect {

        @Getter
        @Setter
        private boolean mEnabled = true;

        public CustomEdgeEffect(@NonNull Context context) {
            super(context);
        }

        @Override
        public boolean draw(Canvas canvas) {
            return mEnabled && super.draw(canvas);
        }
    }

    public abstract void setEdgeEffectColors(@ColorInt int color);

    public abstract void setEdgeEffectsEnabled(boolean enabled);

    public abstract void setEdgeEffectColor(@NonNull Side side, @ColorInt int color);

    public abstract void setEdgeEffectEnabled(@NonNull Side side, boolean enabled);

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static final class EdgeEffectsLollipop extends EdgeEffects {

        @NonNull
        private final Class<? extends View> mViewClass;

        @NonNull
        private final CustomEdgeEffectHost mHost;

        @NonNull
        private final HashMap<Side, CustomEdgeEffect> mEdges = new HashMap<>();

        public EdgeEffectsLollipop(@NonNull Class<? extends View> viewClass,
                                   @NonNull CustomEdgeEffectHost customEdgeEffectHost,
                                   @NonNull Context context) {
            mViewClass = viewClass;
            mHost = customEdgeEffectHost;

            for (EdgeEffectModel model : mHost.getEdgeEffectModels()) {
                CustomEdgeEffect edgeEffect = new CustomEdgeEffect(context);
                if (replaceEdgeEffect(context, model.getFieldName(), edgeEffect, model.isCompat())) {
                    mEdges.put(model.getSide(), edgeEffect);
                }
            }
        }

        @CheckResult
        private boolean replaceEdgeEffect(@NonNull Context context,
                                          @NonNull String fieldName,
                                          @NonNull CustomEdgeEffect edgeEffect,
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
                                                @NonNull CustomEdgeEffect edgeEffect) {
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
                                          @NonNull CustomEdgeEffect edgeEffect) {
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

        @Override
        public void setEdgeEffectColors(@ColorInt int color) {
            for (CustomEdgeEffect edgeEffect : mEdges.values()) {
                if (edgeEffect != null) {
                    edgeEffect.setColor(color);
                }
            }
        }

        @Override
        public void setEdgeEffectsEnabled(boolean enabled) {
            for (CustomEdgeEffect edgeEffect : mEdges.values()) {
                if (edgeEffect != null) {
                    edgeEffect.setEnabled(enabled);
                }
            }
        }

        @Override
        public void setEdgeEffectColor(@NonNull Side side, @ColorInt int color) {
            CustomEdgeEffect edgeEffect = mEdges.get(side);
            if (edgeEffect != null) {
                edgeEffect.setColor(color);
            }
        }

        @Override
        public void setEdgeEffectEnabled(@NonNull Side side, boolean enabled) {
            CustomEdgeEffect edgeEffect = mEdges.get(side);
            if (edgeEffect != null) {
                edgeEffect.setEnabled(enabled);
            }
        }
    }

    private static final class EdgeEffectsHolo extends EdgeEffects {

        @Override
        public void setEdgeEffectColors(@ColorInt int color) {
            // TODO: Something??
        }

        @Override
        public void setEdgeEffectsEnabled(boolean enabled) {
            // TODO: Something??
        }

        @Override
        public void setEdgeEffectColor(@NonNull Side side, @ColorInt int color) {
            // TODO: Something??
        }

        @Override
        public void setEdgeEffectEnabled(@NonNull Side side, boolean enabled) {
            // TODO: Something??
        }
    }
}
