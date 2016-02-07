package me.oriley.vista.scrollviews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import lombok.NonNull;
import lombok.experimental.Delegate;
import me.oriley.vista.scrollviews.EdgeEffects.Side;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class VistaRecyclerView extends RecyclerView implements CustomEdgeEffectHost {

    private static final String LEFT_EDGE = "mLeftGlow";
    private static final String TOP_EDGE = "mTopGlow";
    private static final String RIGHT_EDGE = "mRightGlow";
    private static final String BOTTOM_EDGE = "mBottomGlow";

    @Delegate
    @NonNull
    private final EdgeEffects mEdgeEffects;

    public VistaRecyclerView(Context context) {
        this(context, null);
    }

    public VistaRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VistaRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mEdgeEffects = EdgeEffects.get(RecyclerView.class, this, context);
    }

    @NonNull
    @Override
    public final List<EdgeEffectModel> getEdgeEffectModels() {
        List<EdgeEffectModel> models = new ArrayList<>();
        models.add(new EdgeEffectModel(LEFT_EDGE, Side.LEFT, true));
        models.add(new EdgeEffectModel(TOP_EDGE, Side.TOP, true));
        models.add(new EdgeEffectModel(RIGHT_EDGE, Side.RIGHT, true));
        models.add(new EdgeEffectModel(BOTTOM_EDGE, Side.BOTTOM, true));
        return models;
    }
}
