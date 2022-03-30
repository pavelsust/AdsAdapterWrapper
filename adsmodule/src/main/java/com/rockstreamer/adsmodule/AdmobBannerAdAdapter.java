package com.rockstreamer.adsmodule;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;

public class AdmobBannerAdAdapter extends RecyclerViewAdapterWrapper {

    private static final int TYPE_FB_NATIVE_ADS = 900;
    private static final int DEFAULT_AD_ITEM_INTERVAL = 4;

    private final Param mParam;


    private AdmobBannerAdAdapter(Param param){
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


    public AdmobBannerAdAdapter build() {
        return new AdmobBannerAdAdapter(mParam);
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
        /*if(position==1|| position==4)return true;*/
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
            AdRequest adRequest = new AdRequest.Builder().build();

            admobBannerHolder.adView.loadAd(adRequest);

            admobBannerHolder.adView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    Log.d("ADMOB" , "Ads loaded");
                }

                @Override
                public void onAdFailedToLoad(LoadAdError adError) {
                    Log.d("ADMOB" , "Ads error "+adError.toString());
                }

                @Override
                public void onAdOpened() {
                    // Code to be executed when an ad opens an overlay that
                    // covers the screen.
                }

                @Override
                public void onAdClicked() {
                    // Code to be executed when the user clicks on an ad.
                }

                @Override
                public void onAdClosed() {
                    // Code to be executed when the user is about to return
                    // to the app after tapping on an ad.
                }
            });

        } else {
            super.onBindViewHolder(holder, convertAdPosition2OrgPosition(position));
        }
    }

    private static class AdmobBannerHolder extends RecyclerView.ViewHolder{
        AdView adView;
        AdmobBannerHolder(View view){
            super(view);
            adView = view.findViewById(R.id.adView);
        }
    }


    public static class Builder{
        private final Param mParam;

        private Builder(Param param){
            this.mParam = param;
        }

        public static Builder with(String placementId, RecyclerView.Adapter wrapped, String layout){
            Param param = new Param();
            param.admobNativeId = placementId;
            param.adapter = wrapped;
            param.adItemInterval = DEFAULT_AD_ITEM_INTERVAL;
            param.itemContainerLayoutRes = R.layout.item_admob_banner;
            param.itemContainerId = R.id.adView;
            param.forceReloadAdOnBind = true;
            return new Builder(param);
        }

        public Builder adItemInterval(int interval) {
            mParam.adItemInterval = interval;
            return this;
        }

        public AdmobBannerAdAdapter build() {
            return new AdmobBannerAdAdapter(mParam);
        }

    }

}
