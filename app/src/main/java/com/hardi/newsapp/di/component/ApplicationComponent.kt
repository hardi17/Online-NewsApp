package com.hardi.newsapp.di.component

import android.content.Context
import com.hardi.NewsApplication
import com.hardi.newsapp.data.api.NetworkService
import com.hardi.newsapp.data.repository.TopHeadlineRepository
import com.hardi.newsapp.di.ApplicationContext
import com.hardi.newsapp.di.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: NewsApplication)

    @ApplicationContext
    fun getContext(): Context

    fun getNetworkService() : NetworkService

    fun getTopHeadlineRepository() : TopHeadlineRepository

}