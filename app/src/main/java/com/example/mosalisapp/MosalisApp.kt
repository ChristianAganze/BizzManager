package com.example.mosalisapp

import android.app.Application
import com.example.mosalisapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class MosalisApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@MosalisApp)
            androidLogger()
            modules(appModule)

        }
    }

}