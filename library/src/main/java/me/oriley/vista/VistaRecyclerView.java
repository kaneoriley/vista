package me.oriley.vista;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import me.oriley.vista.VistaEdgeEffectHelper.Side;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class VistaRecyclerView extends RecyclerView implements VistaEdgeEffectHost {

    private static final String LEFT_EDGE = "mLeftGlow";
    private static final String TOP_EDGE = "mTopGlow";
    private static final String RIGHT_EDGE = "mRightGlow";
    private static final String BOTTOM_EDGE = "mBottomGlow";

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
        mEdgeEffects = new VistaEdgeEffectHelper(RecyclerView.class, this, context);
    }


    @NonNull
    @Override
    public final List<VistaEdgeEffectModel> getEdgeEffectModels() {
        List<VistaEdgeEffectModel> models = new ArrayList<>();
        models.add(new VistaEdgeEffectModel(LEFT_EDGE, Side.LEFT, true));
        models.add(new VistaEdgeEffectModel(TOP_EDGE, Side.TOP, true));
        models.add(new VistaEdgeEffectModel(RIGHT_EDGE, Side.RIGHT, true));
        models.add(new VistaEdgeEffectModel(BOTTOM_EDGE, Side.BOTTOM, true));
        return models;
    }

    @Override
    public void setEdgeEffectColors(@ColorInt int color) {
        mEdgeEffects.setEdgeEffectColors(color);
    }

    @Override
    public void setEdgeEffectColor(@NonNull Side side, @ColorInt int color) {
        mEdgeEffects.setEdgeEffectColor(side, color);
    }
}
