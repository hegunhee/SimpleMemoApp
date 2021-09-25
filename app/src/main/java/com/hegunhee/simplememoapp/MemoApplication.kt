package com.hegunhee.simplememoapp

import android.app.Application
import com.hegunhee.simplememoapp.di.module
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MemoApplication : Application(){

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MemoApplication)
            modules(module)
        }
    }
}