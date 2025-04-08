package org.example.project

import android.app.Application
import org.example.project.di.initKoin
import org.koin.android.ext.koin.androidContext

class BookApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            // since it is a android specific, so we may require context for performing android related tasks
            androidContext(this@BookApplication)
        }
    }
}