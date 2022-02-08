package com.rockstreamer.adsmodule.controller

import android.app.Application
import com.facebook.ads.AudienceNetworkAds

class AdsController:Application() {


    override fun onCreate() {
        super.onCreate()
        AudienceNetworkAds.initialize(this);

    }
}