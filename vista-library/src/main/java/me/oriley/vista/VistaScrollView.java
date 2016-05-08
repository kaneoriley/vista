package me.oriley.vista;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

public class VistaScrollView extends ScrollView implements VistaEdgeEffectHost {

    private static final String TOP_EDGE = "mEdgeGlowTop";
    private static final String BOTTOM_EDGE = "mEdgeGlowBottom";

    @NonNull
    private final VistaEdgeEffectHelper mEdgeEffects;


    public VistaScrollView(Context context) {
        this(context, null);
    }

    public VistaScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VistaScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mEdgeEffects = new VistaEdgeEffectHelper(ScrollView.class, this, context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public VistaScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mEdgeEffects = new VistaEdgeEffectHelper(AbsListView.class, this, context, attrs);
    }


    @NonNull
    @Override
    public final List<VistaEdgeEffectModel> getEdgeEffectModels() {
        List<VistaEdgeEffectModel> models = new ArrayList<>();
        models.add(new VistaEdgeEffectModel(TOP_EDGE, VistaEdgeEffectHelper.Side.TOP, false));
        models.add(new VistaEdgeEffectModel(BOTTOM_EDGE, VistaEdgeEffectHelper.Side.BOTTOM, false));
        return models;
    }

    @Override
    public void setEdgeEffectColors(@ColorInt int color) {
        mEdgeEffects.setEdgeEffectColors(color);
    }

    @Override
    public void setEdgeEffectColor(@NonNull VistaEdgeEffectHelper.Side side, @ColorInt int color) {
        mEdgeEffects.setEdgeEffectColor(side, color);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mEdgeEffects.refreshEdges();
    }
}
