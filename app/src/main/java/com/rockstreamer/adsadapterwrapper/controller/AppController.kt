package com.rockstreamer.adsadapterwrapper.controller

import android.app.Application
import androidx.multidex.MultiDex
import com.facebook.ads.AudienceNetworkAds

class AppController :Application(){

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        AudienceNetworkAds.initialize(this)
    }
}