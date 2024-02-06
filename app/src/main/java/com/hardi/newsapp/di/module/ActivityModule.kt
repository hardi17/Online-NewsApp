package com.hardi.newsapp.di.module

import com.hardi.newsapp.data.repository.*
import com.hardi.newsapp.ui.countryactivity.CountriesAdapter
import com.hardi.newsapp.ui.languageactivity.LanguagesAdapter
import com.hardi.newsapp.ui.newslist.NewsListAdapter
import com.hardi.newsapp.ui.newssources.NewsSourcesAdapter
import com.hardi.newsapp.ui.searchactivity.SearchViewAdapter
import com.hardi.newsapp.ui.topheadline.TopHeadlineAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class ActivityModule {

    @ActivityScoped
    @Provides
    fun provideTopHeadlineAdapter() = TopHeadlineAdapter(ArrayList())

    @ActivityScoped
    @Provides
    fun provideNewsSourcesAdapter() = NewsSourcesAdapter(ArrayList())

    @ActivityScoped
    @Provides
    fun provideCountriesAdapter() = CountriesAdapter(ArrayList())


    @ActivityScoped
    @Provides
    fun provideLanguagesAdapter() = LanguagesAdapter(ArrayList())


    @ActivityScoped
    @Provides
    fun provideSourcesAdapter() = NewsListAdapter(ArrayList())


    @ActivityScoped
    @Provides
    fun provideSearchAdapter() = SearchViewAdapter(ArrayList())
}