package com.example.adopetme

import android.app.Application
import com.example.adopetme.di.repositoryModule
import com.example.adopetme.di.viewModelModule
import org.koin.android.ext.koin.androidLogger
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class AdopetMeApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(
                if(BuildConfig.DEBUG)
                Level.ERROR
                else
                    Level.NONE
            )
            androidContext(this@AdopetMeApplication)
            modules(
                repositoryModule,
                viewModelModule
            )
        }
    }
}