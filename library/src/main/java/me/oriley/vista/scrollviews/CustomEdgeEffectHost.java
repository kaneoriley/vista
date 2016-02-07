package me.oriley.vista.scrollviews;

import lombok.NonNull;

import java.util.List;

interface CustomEdgeEffectHost {

    @NonNull
    List<EdgeEffectModel> getEdgeEffectModels();
}
