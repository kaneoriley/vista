package me.oriley.vista.textviews;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.Log;
import lombok.NonNull;
import me.oriley.vista.utils.ContextUtils;

import java.util.HashMap;

@SuppressWarnings("unused")
public final class VistaTypefaceCache {

    private static final String TAG = VistaTypefaceCache.class.getSimpleName();

    private VistaTypefaceCache() {
        throw new IllegalAccessError("no instances");
    }

    @NonNull
    private static final HashMap<String, Typeface> sTypefaces = new HashMap<>();

    @Nullable
    private static Context mContext;

    @Nullable
    public static Typeface getTypeface(@NonNull String fontFile) {
        initContext();
        if (mContext == null) {
            Log.e(TAG, "Context not available when retrieving font: " + fontFile);
            return null;
        }

        Typeface typeface = null;
        try {
            if (sTypefaces.containsKey(fontFile)) {
                typeface = sTypefaces.get(fontFile);
            }
            if (typeface == null) {
                typeface = Typeface.createFromAsset(mContext.getAssets(), fontFile);
            }
        } catch (RuntimeException e) {
            Log.e(TAG, "exception loading typeface: " + fontFile);
            e.printStackTrace();
        } finally {
            if (typeface != null) {
                sTypefaces.put(fontFile, typeface);
            }
        }
        return typeface;
    }

    public static void init(@NonNull Context context) {
        if (mContext == null) {
            mContext = context;
        } else {
            throw new IllegalStateException("Already initialised!");
        }
    }

    private static void initContext() {
        if (mContext == null) {
            mContext = ContextUtils.currentApplication();
        }
    }
}
