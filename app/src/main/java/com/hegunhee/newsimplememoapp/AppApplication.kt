package com.hegunhee.newsimplememoapp


import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.core.context.startKoin

@HiltAndroidApp
class AppApplication: Application() {
}