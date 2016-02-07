package me.oriley.vista.utils;

import android.support.annotation.Nullable;
import lombok.experimental.UtilityClass;

@SuppressWarnings("unused")
@UtilityClass
public final class StringUtils {

    public static boolean equals(@Nullable CharSequence a, @Nullable CharSequence b) {
        return (a == null) ? (b == null) : a.toString().equals(b);
    }

    public static boolean equalsNotNull(@Nullable CharSequence a, @Nullable CharSequence b) {
        return (a != null) && a.toString().equals(b);
    }
}
