package com.app.businessmanager

import android.app.Application
import com.app.businessmanager.di.firebaseModule
import com.app.businessmanager.di.repositoryModule
import com.app.businessmanager.di.useCaseModule
import com.app.businessmanager.di.viewModelModule
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
