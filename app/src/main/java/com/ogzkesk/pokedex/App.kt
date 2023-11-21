package com.ogzkesk.pokedex

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        setLogging()
    }

    private fun setLogging(){
        Timber.plant(Timber.DebugTree())
    }

}