package me.oriley.vista;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

import java.util.List;

interface VistaEdgeEffectHost {

    @NonNull
    List<VistaEdgeEffectModel> getEdgeEffectModels();

    void setEdgeEffectColors(@ColorInt int color);

    void setEdgeEffectColor(@NonNull VistaEdgeEffectHelper.Side side, @ColorInt int color);

    @NonNull
    Context getContext();

    int getMeasuredWidth();

    int getMeasuredHeight();
}
