package com.rockstreamer.adsadapterwrapper.controller

import android.app.Application
import androidx.multidex.MultiDex
import com.facebook.ads.AudienceNetworkAds
import com.google.android.gms.ads.MobileAds

class AppController :Application(){

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        AudienceNetworkAds.initialize(this)
        MobileAds.initialize(this)
    }
}