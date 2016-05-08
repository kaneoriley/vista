package me.oriley.vista;

import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

import java.util.List;

interface VistaEdgeEffectHost {

    @NonNull
    List<VistaEdgeEffectModel> getEdgeEffectModels();

    void setEdgeEffectColors(@ColorInt int color);

    void setEdgeEffectColor(@NonNull VistaEdgeEffectHelper.Side side, @ColorInt int color);
}
