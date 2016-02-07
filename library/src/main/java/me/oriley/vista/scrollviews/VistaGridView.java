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

package me.oriley.vista.scrollviews;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import android.widget.AbsListView;
import android.widget.GridView;
import lombok.NonNull;
import lombok.experimental.Delegate;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class VistaGridView extends GridView implements CustomEdgeEffectHost {

    private static final String TOP_EDGE = "mEdgeGlowTop";
    private static final String BOTTOM_EDGE = "mEdgeGlowBottom";

    @Delegate
    @NonNull
    private final EdgeEffects mEdgeEffects;

    public VistaGridView(Context context) {
        this(context, null);
    }

    public VistaGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VistaGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mEdgeEffects = EdgeEffects.get(AbsListView.class, this, context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public VistaGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mEdgeEffects = EdgeEffects.get(AbsListView.class, this, context);
    }

    @Nullable
    public View getViewByPosition(int position) {
        int firstPosition = this.getFirstVisiblePosition();
        int lastPosition = this.getLastVisiblePosition();
        if ((position < firstPosition) || (position > lastPosition)) return null;
        return getChildAt(position - firstPosition);
    }

    @NonNull
    @Override
    public final List<EdgeEffectModel> getEdgeEffectModels() {
        List<EdgeEffectModel> models = new ArrayList<>();
        models.add(new EdgeEffectModel(TOP_EDGE, EdgeEffects.Side.TOP, false));
        models.add(new EdgeEffectModel(BOTTOM_EDGE, EdgeEffects.Side.BOTTOM, false));
        return models;
    }
}