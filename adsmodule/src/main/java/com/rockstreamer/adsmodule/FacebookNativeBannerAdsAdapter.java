package com.rockstreamer.adsmodule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.NativeBannerAd;

import java.util.ArrayList;
import java.util.List;


public class FacebookNativeBannerAdsAdapter extends RecyclerViewAdapterWrapper {

    private static final int TYPE_FB_NATIVE_ADS = 900;
    private static final int DEFAULT_AD_ITEM_INTERVAL = 10;

    private final Param mParam;

    private FacebookNativeBannerAdsAdapter(Param param) {
        super(param.adapter);
        this.mParam = param;

        assertConfig();
        setSpanAds();
    }

    private void assertConfig() {
        if (mParam.gridLayoutManager != null) {
            //if user set span ads
            int nCol = mParam.gridLayoutManager.getSpanCount();
            if (mParam.adItemInterval % nCol != 0) {
                throw new IllegalArgumentException(String.format("The adItemInterval (%d) is not divisible by number of columns in GridLayoutManager (%d)", mParam.adItemInterval, nCol));
            }
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

    private void onBindAdViewHolder(final RecyclerView.ViewHolder holder) {
        final AdViewHolder adHolder = (AdViewHolder) holder;

        NativeBannerAd nativeBannerAd = new NativeBannerAd(adHolder.getContext(), ""+mParam.facebookPlacementId);
        NativeAdListener nativeAdListener = new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {

            }

            @Override
            public void onError(Ad ad, AdError adError) {

            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Race condition, load() called again before last ad was displayed
                if (nativeBannerAd == null || nativeBannerAd != ad) {
                    return;
                }

                nativeBannerAd.unregisterView();

                adHolder.nativeAdCallToAction.setText(nativeBannerAd.getAdCallToAction());
                adHolder.nativeAdCallToAction.setVisibility(
                        nativeBannerAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
                adHolder.nativeAdTitle.setText(nativeBannerAd.getAdvertiserName());



                adHolder.nativeAdSocialContext.setText(nativeBannerAd.getAdSocialContext());

                List<View> clickableViews = new ArrayList<>();
                clickableViews.add(adHolder.nativeAdTitle);
                clickableViews.add(adHolder.nativeAdCallToAction);
                nativeBannerAd.registerViewForInteraction(adHolder.view, adHolder.nativeAdIconView, clickableViews);
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }

        };

        nativeBannerAd.loadAd(
                nativeBannerAd.buildLoadAdConfig()
                        .withAdListener(nativeAdListener)
                        .build());
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_FB_NATIVE_ADS) {
            onBindAdViewHolder(holder);
        } else {
            super.onBindViewHolder(holder, convertAdPosition2OrgPosition(position));
        }
    }

    private RecyclerView.ViewHolder onCreateAdViewHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View adLayoutOutline = inflater
                .inflate(mParam.itemContainerLayoutRes, parent, false);
        ViewGroup vg = adLayoutOutline.findViewById(mParam.itemContainerId);

        CardView adLayoutContent = (CardView) inflater.inflate(R.layout.item_native_facebook_banner_child, parent, false);
        vg.addView(adLayoutContent);
        return new AdViewHolder(adLayoutOutline);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FB_NATIVE_ADS) {
            return onCreateAdViewHolder(parent);
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    private void setSpanAds() {
        if (mParam.gridLayoutManager == null) {
            return ;
        }
        final GridLayoutManager.SpanSizeLookup spl = mParam.gridLayoutManager.getSpanSizeLookup();
        mParam.gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (isAdPosition(position)){
                    return spl.getSpanSize(position);
                }
                return 1;
            }
        });
    }

    private static class Param {
        String facebookPlacementId;
        RecyclerView.Adapter adapter;
        int adItemInterval;
        boolean forceReloadAdOnBind;

        @LayoutRes
        int itemContainerLayoutRes;

        @IdRes
        int itemContainerId;

        GridLayoutManager gridLayoutManager;
    }

    public static class Builder {
        private final Param mParam;

        private Builder(Param param) {
            mParam = param;
        }

        public static Builder with(String placementId, int adsInterval,RecyclerView.Adapter wrapped) {
            Param param = new Param();
            param.facebookPlacementId = placementId;
            param.adapter = wrapped;

            //default value
            param.adItemInterval = adsInterval;
            param.itemContainerLayoutRes = R.layout.item_facebook_native_banner;
            param.itemContainerId = R.id.native_banner_ad_container;
            param.forceReloadAdOnBind = true;
            return new Builder(param);
        }

        public Builder adItemInterval(int interval) {
            mParam.adItemInterval = interval;
            return this;
        }

        public Builder adLayout(@LayoutRes int layoutContainerRes, @IdRes int itemContainerId) {
            mParam.itemContainerLayoutRes = layoutContainerRes;
            mParam.itemContainerId = itemContainerId;
            return this;
        }

        public FacebookNativeBannerAdsAdapter build() {
            return new FacebookNativeBannerAdsAdapter(mParam);
        }

        public Builder enableSpanRow(GridLayoutManager layoutManager) {
            mParam.gridLayoutManager = layoutManager;
            return this;
        }

        public Builder forceReloadAdOnBind(boolean forced) {
            mParam.forceReloadAdOnBind = forced;
            return this;
        }


    }

    private static class AdViewHolder extends RecyclerView.ViewHolder {
        TextView nativeAdTitle;
        TextView nativeAdSocialContext;

        MediaView nativeAdIconView;
        Button nativeAdCallToAction;
        View view;


        AdViewHolder(View view) {
            super(view);

            nativeAdTitle = view.findViewById(R.id.native_ad_title);
            nativeAdSocialContext = view.findViewById(R.id.native_ad_social_context);
            nativeAdIconView = view.findViewById(R.id.native_icon_view);
            nativeAdCallToAction = view.findViewById(R.id.native_ad_call_to_action);
            this.view = view;
        }

        Context getContext() {
            return nativeAdTitle.getContext();
        }
    }

}
