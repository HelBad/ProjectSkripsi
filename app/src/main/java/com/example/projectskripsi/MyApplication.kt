package com.example.projectskripsi

import android.app.Application
import android.content.Context
import com.example.projectskripsi.core.authModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    authModule
                )
            )
        }
    }

    companion object {
        lateinit var instance: MyApplication
            private set
    }
}