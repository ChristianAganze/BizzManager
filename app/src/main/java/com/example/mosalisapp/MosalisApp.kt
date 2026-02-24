package com.example.mosalisapp

import android.app.Application
import com.example.mosalisapp.di.firebaseModule
import com.example.mosalisapp.di.repositoryModule
import com.example.mosalisapp.di.useCaseModule
import com.example.mosalisapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BusinessApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@BusinessApp)
            modules(
                firebaseModule,
                repositoryModule,
                useCaseModule,
                viewModelModule
            )
        }
    }
}
