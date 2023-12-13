package com.hardi.newsapp.di.module

import android.app.Activity
import android.content.Context
import com.hardi.newsapp.di.ActivityContext
import com.hardi.newsapp.di.ActivityScope
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: Activity) {

    @ActivityContext
    @Provides
    fun provideContext(): Context{
        return activity
    }
}