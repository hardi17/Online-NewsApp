package com.hardi

import android.app.Application
import com.hardi.newsapp.di.component.ApplicationComponent
import com.hardi.newsapp.di.component.DaggerApplicationComponent
import com.hardi.newsapp.di.module.ApplicationModule

class NewsApplication : Application() {

    private lateinit var applicationComponent : ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        injectDependencies()
    }

    private fun injectDependencies() {
        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
        applicationComponent.inject(this)
    }
}