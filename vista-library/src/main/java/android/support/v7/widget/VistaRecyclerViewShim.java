package android.support.v7.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

public class VistaRecyclerViewShim extends RecyclerView {

    private static final String TAG = VistaRecyclerViewShim.class.getSimpleName();


    public VistaRecyclerViewShim(Context context) {
        this(context, null);
    }

    public VistaRecyclerViewShim(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VistaRecyclerViewShim(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    void ensureTopGlow() {
        Log.d(TAG, "Ensure top glow");
    }

    @Override
    void invalidateGlows() {
        Log.d(TAG, "Invalidating glows");
    }
}
