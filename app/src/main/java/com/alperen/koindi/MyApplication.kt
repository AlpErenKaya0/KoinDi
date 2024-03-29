package com.alperen.koindi

import android.app.Application
import com.alperen.koindi.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@MyApplication)
            modules(appModule)
        }
    }
}