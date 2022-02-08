package com.rockstreamer.adsadapterwrapper.controller

import android.app.Application
import androidx.multidex.MultiDex

class AppController :Application(){

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
    }
}