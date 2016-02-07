package me.oriley.vista.scrollviews;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Accessors(prefix = "m")
@Getter
class EdgeEffectModel {

    @NonNull
    private final String mFieldName;

    @NonNull
    private final EdgeEffects.Side mSide;

    private final boolean mCompat;

    EdgeEffectModel(@NonNull String fieldName, @NonNull EdgeEffects.Side side, boolean compat) {
        mFieldName = fieldName;
        mSide = side;
        mCompat = compat;
    }
}
