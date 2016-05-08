/*
 * Copyright (C) 2016 Kane O'Riley
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package me.oriley.vista;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.util.AttributeSet;

import android.widget.AbsListView;
import android.widget.GridView;
import me.oriley.vista.VistaEdgeEffectHelper.Side;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class VistaGridView extends GridView implements VistaEdgeEffectHost {

    private static final String TOP_EDGE = "mEdgeGlowTop";
    private static final String BOTTOM_EDGE = "mEdgeGlowBottom";

    @NonNull
    private final VistaEdgeEffectHelper mEdgeEffects;


    public VistaGridView(Context context) {
        this(context, null);
    }

    public VistaGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VistaGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mEdgeEffects = new VistaEdgeEffectHelper(AbsListView.class, this, context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public VistaGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mEdgeEffects = new VistaEdgeEffectHelper(AbsListView.class, this, context, attrs);
    }


    @NonNull
    @Override
    public final List<VistaEdgeEffectModel> getEdgeEffectModels() {
        List<VistaEdgeEffectModel> models = new ArrayList<>();
        models.add(new VistaEdgeEffectModel(TOP_EDGE, Side.TOP, false));
        models.add(new VistaEdgeEffectModel(BOTTOM_EDGE, Side.BOTTOM, false));
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

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mEdgeEffects.refreshEdges();
    }
}