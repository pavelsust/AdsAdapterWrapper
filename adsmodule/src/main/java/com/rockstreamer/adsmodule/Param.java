package com.rockstreamer.adsmodule;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Param {

    String admobNativeId;
    RecyclerView.Adapter adapter;
    int adItemInterval;
    boolean forceReloadAdOnBind;

    int layout;

    @LayoutRes
    int itemContainerLayoutRes;

    @IdRes
    int itemContainerId;

    GridLayoutManager gridLayoutManager;


}
