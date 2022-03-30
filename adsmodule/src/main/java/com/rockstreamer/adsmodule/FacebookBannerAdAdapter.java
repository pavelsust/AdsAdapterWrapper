package com.rockstreamer.adsmodule;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.recyclerview.widget.RecyclerView;


import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.google.android.gms.ads.LoadAdError;


public class FacebookBannerAdAdapter extends RecyclerViewAdapterWrapper {

    private static final int TYPE_FB_NATIVE_ADS = 900;
    private static final int DEFAULT_AD_ITEM_INTERVAL = 4;

    private final Param mParam;


    private FacebookBannerAdAdapter(Param param){
        super(param.adapter);
        this.mParam = param;
        assertConfig();
    }


    private void assertConfig() {
        if (mParam.gridLayoutManager != null) {
            throw new IllegalArgumentException("Banner Ads don't support GridLayout");

        }
    }

    private int convertAdPosition2OrgPosition(int position) {
        return position - (position + 1) / (mParam.adItemInterval + 1);
    }


    @Override
    public int getItemCount() {
        int realCount = super.getItemCount();
        return realCount + realCount / mParam.adItemInterval;
    }

    @Override
    public int getItemViewType(int position) {
        if (isAdPosition(position)) {
            return TYPE_FB_NATIVE_ADS;
        }
        return super.getItemViewType(convertAdPosition2OrgPosition(position));
    }
    private boolean isAdPosition(int position) {
        return (position + 1) % (mParam.adItemInterval + 1) == 0;
    }



    private RecyclerView.ViewHolder onCreateAdViewHolder(ViewGroup parent) {
        return new AdmobBannerHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admob_banner, parent ,false));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FB_NATIVE_ADS) {
            return onCreateAdViewHolder(parent);
        }
        return super.onCreateViewHolder(parent, viewType);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_FB_NATIVE_ADS) {
            final AdmobBannerHolder admobBannerHolder = (AdmobBannerHolder) holder;

           com.facebook.ads.AdView adView = new AdView(mParam.context , ""+mParam.admobNativeId , AdSize.BANNER_320_50);
            admobBannerHolder.frameLayout.addView(adView);
            adView.loadAd();

        } else {
            super.onBindViewHolder(holder, convertAdPosition2OrgPosition(position));
        }
    }

    private static class AdmobBannerHolder extends RecyclerView.ViewHolder{
        FrameLayout frameLayout;
        AdmobBannerHolder(View view){
            super(view);
            frameLayout = view.findViewById(R.id.admob_banner_container);
        }
    }

    public static class Builder{
        private final Param mParam;

        private Builder(Param param){
            this.mParam = param;
        }

        public static Builder with(Context context , String placementId, RecyclerView.Adapter wrapped){
            Param param = new Param();
            param.admobNativeId = placementId;
            param.adapter = wrapped;
            param.adItemInterval = DEFAULT_AD_ITEM_INTERVAL;
            param.itemContainerLayoutRes = R.layout.item_admob_banner;
            param.itemContainerId = R.id.admob_banner_container;
            param.forceReloadAdOnBind = true;
            param.context = context;
            return new Builder(param);
        }

        public Builder adItemInterval(int interval) {
            mParam.adItemInterval = interval;
            return this;
        }

        public FacebookBannerAdAdapter build() {
            return new FacebookBannerAdAdapter(mParam);
        }
    }
}
