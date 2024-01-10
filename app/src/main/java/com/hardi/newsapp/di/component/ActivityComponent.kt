package com.hardi.newsapp.di.component

import com.hardi.newsapp.di.ActivityScope
import com.hardi.newsapp.di.module.ActivityModule
import com.hardi.newsapp.ui.countryactivity.CountriesActivity
import com.hardi.newsapp.ui.languageactivity.LanguagesActivity
import com.hardi.newsapp.ui.newslist.NewsListActivity
import com.hardi.newsapp.ui.newssources.NewsSourcesActivity
import com.hardi.newsapp.ui.searchactivity.SearchActivity
import com.hardi.newsapp.ui.topheadline.TopHeadlineActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: TopHeadlineActivity)

    fun inject(activity: NewsSourcesActivity)

    fun inject(activity: NewsListActivity)

    fun inject(activity: SearchActivity)

    fun inject(activity: CountriesActivity)

    fun inject(activity: LanguagesActivity)
}