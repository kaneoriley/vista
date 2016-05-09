package me.oriley.vista;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import me.oriley.vista.VistaEdgeEffectHelper.Side;

import java.lang.reflect.Field;
import java.util.*;

@SuppressWarnings("unused")
public class VistaRecyclerView extends RecyclerView implements VistaEdgeEffectHost {

    private static final Map<Side, Field> FIELD_MAP;

    static {
        Map<Side, Field> map = new HashMap<>();

        VistaEdgeEffectHelper.addEdgeEffectFieldIfFound(map, RecyclerView.class, Side.TOP, "mTopGlow");
        VistaEdgeEffectHelper.addEdgeEffectFieldIfFound(map, RecyclerView.class, Side.LEFT, "mLeftGlow");
        VistaEdgeEffectHelper.addEdgeEffectFieldIfFound(map, RecyclerView.class, Side.RIGHT, "mRightGlow");
        VistaEdgeEffectHelper.addEdgeEffectFieldIfFound(map, RecyclerView.class, Side.BOTTOM, "mBottomGlow");

        FIELD_MAP = Collections.unmodifiableMap(map);
    }

    @NonNull
    private final VistaEdgeEffectHelper mEdgeEffects;


    public VistaRecyclerView(Context context) {
        this(context, null);
    }

    public VistaRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VistaRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mEdgeEffects = new VistaEdgeEffectHelper(RecyclerView.class, this, context, attrs);
    }


    @Override
    public void setEdgeEffectColors(@ColorInt int color) {
        mEdgeEffects.setEdgeEffectColors(color);
    }

    @Override
    public void setEdgeEffectColor(@NonNull Side side, @ColorInt int color) {
        mEdgeEffects.setEdgeEffectColor(side, color);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mEdgeEffects.refreshEdges(FIELD_MAP, true);
    }
}
