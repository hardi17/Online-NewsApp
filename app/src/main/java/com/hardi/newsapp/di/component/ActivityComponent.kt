package com.hardi.newsapp.di.component

import com.hardi.newsapp.di.ActivityScope
import com.hardi.newsapp.di.module.ActivityModule
import com.hardi.newsapp.ui.topheadline.TopHeadlineActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: TopHeadlineActivity)
}