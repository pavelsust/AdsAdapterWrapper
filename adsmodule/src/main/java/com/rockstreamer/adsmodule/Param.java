package com.rockstreamer.adsmodule;

import android.content.Context;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Param {

    String admobNativeId;
    RecyclerView.Adapter adapter;
    int adItemInterval;
    boolean forceReloadAdOnBind;
    Context context;

    int layout;

    @LayoutRes
    int itemContainerLayoutRes;

    @IdRes
    int itemContainerId;

    GridLayoutManager gridLayoutManager;


}
