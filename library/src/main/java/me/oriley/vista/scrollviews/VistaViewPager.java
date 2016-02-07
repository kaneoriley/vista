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

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import lombok.NonNull;
import lombok.experimental.Delegate;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class VistaViewPager extends ViewPager implements CustomEdgeEffectHost {

    private static final String LEFT_EDGE = "mLeftEdge";
    private static final String RIGHT_EDGE = "mRightEdge";

    @Delegate
    @NonNull
    private final EdgeEffects mEdgeEffects;

    public VistaViewPager(Context context) {
        this(context, null);
    }

    public VistaViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mEdgeEffects = EdgeEffects.get(ViewPager.class, this, context);
    }

    @NonNull
    @Override
    public final List<EdgeEffectModel> getEdgeEffectModels() {
        List<EdgeEffectModel> models = new ArrayList<>();
        models.add(new EdgeEffectModel(LEFT_EDGE, EdgeEffects.Side.LEFT, true));
        models.add(new EdgeEffectModel(RIGHT_EDGE, EdgeEffects.Side.RIGHT, true));
        return models;
    }
}