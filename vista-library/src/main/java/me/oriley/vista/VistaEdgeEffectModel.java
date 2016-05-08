package me.oriley.vista;

import android.support.annotation.NonNull;

class VistaEdgeEffectModel {

    @NonNull
    public final String fieldName;

    @NonNull
    public final VistaEdgeEffectHelper.Side side;

    public final boolean isCompat;


    VistaEdgeEffectModel(@NonNull String fieldName, @NonNull VistaEdgeEffectHelper.Side side, boolean isCompat) {
        this.fieldName = fieldName;
        this.side = side;
        this.isCompat = isCompat;
    }
}
