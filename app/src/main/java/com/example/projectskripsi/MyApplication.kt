package com.example.projectskripsi

import android.app.Application
import com.example.projectskripsi.features.auth.di.authModule
import com.example.projectskripsi.features.beranda.di.berandaModule
import com.example.projectskripsi.features.detail.di.detailModule
import com.example.projectskripsi.features.profil.di.profilModule
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
                    authModule,
                    berandaModule,
                    detailModule,
                    profilModule,
                )
            )
        }
    }

    companion object {
        lateinit var instance: MyApplication
            private set
    }
}