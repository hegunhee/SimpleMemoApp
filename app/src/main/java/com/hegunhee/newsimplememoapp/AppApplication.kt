package com.hegunhee.newsimplememoapp


import android.app.Application
import com.hegunhee.newsimplememoapp.di.module
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.core.context.startKoin

@HiltAndroidApp
class AppApplication: Application() {
    override fun onCreate() {
        super.onCreate()

//        startKoin {
//            androidContext(this@AppApplication)
//            androidFileProperties()
//            modules(listOf(module))
//        }
    }
}