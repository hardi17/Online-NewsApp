package com.hardi.newsapp.di.module

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.hardi.newsapp.data.repository.*
import com.hardi.newsapp.di.ActivityContext
import com.hardi.newsapp.ui.base.ViewModelProviderFactory
import com.hardi.newsapp.ui.countryactivity.CountriesAdapter
import com.hardi.newsapp.ui.countryactivity.CountriesViewModel
import com.hardi.newsapp.ui.languageactivity.LanguagesAdapter
import com.hardi.newsapp.ui.languageactivity.LanguagesViewModel
import com.hardi.newsapp.ui.newslist.NewsListAdapter
import com.hardi.newsapp.ui.newslist.NewsListViewModel
import com.hardi.newsapp.ui.newssources.NewsSourcesAdapter
import com.hardi.newsapp.ui.newssources.NewsSourcesViewModel
import com.hardi.newsapp.ui.searchactivity.SearchViewAdapter
import com.hardi.newsapp.ui.searchactivity.SearchViewModel
import com.hardi.newsapp.ui.topheadline.TopHeadlineAdapter
import com.hardi.newsapp.ui.topheadline.TopHeadlineViewModel
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @ActivityContext
    @Provides
    fun provideContext(): Context{
        return activity
    }

    @Provides
    fun provideNewsListViewModel(topHeadlineRepository: TopHeadlineRepository): TopHeadlineViewModel {
        return ViewModelProvider(activity,
            ViewModelProviderFactory(TopHeadlineViewModel::class) {
                TopHeadlineViewModel(topHeadlineRepository)
            })[TopHeadlineViewModel::class.java]
    }

    @Provides
    fun provideTopHeadlineAdapter() = TopHeadlineAdapter(ArrayList())

    @Provides
    fun provideNewsSourcesListViewModel(newsSourcesRepository: NewsSourcesRepository): NewsSourcesViewModel{
        return ViewModelProvider(activity,
            ViewModelProviderFactory(NewsSourcesViewModel::class){
                NewsSourcesViewModel(newsSourcesRepository)
            })[NewsSourcesViewModel::class.java]
    }

    @Provides
    fun provideNewsSourcesAdapter() = NewsSourcesAdapter(ArrayList())

    @Provides
    fun provideCountriesListViewModel(countriesRepository: CountriesRepository): CountriesViewModel{
        return ViewModelProvider(activity,
            ViewModelProviderFactory(CountriesViewModel::class){
                CountriesViewModel(countriesRepository)
            })[CountriesViewModel::class.java]
    }

    @Provides
    fun provideCountriesAdapter() = CountriesAdapter(ArrayList())

    @Provides
    fun provideLanguagesListViewModel(languagesRepository: LanguagesRepository): LanguagesViewModel{
        return ViewModelProvider(activity,
            ViewModelProviderFactory(LanguagesViewModel::class){
                LanguagesViewModel(languagesRepository)
            })[LanguagesViewModel::class.java]
    }

    @Provides
    fun provideLanguagesAdapter() = LanguagesAdapter(ArrayList())

    @Provides
    fun provideSourcesViewModel(topHeadlineRepository: TopHeadlineRepository): NewsListViewModel {
        return ViewModelProvider(activity,
            ViewModelProviderFactory(NewsListViewModel::class) {
                NewsListViewModel(topHeadlineRepository)
            })[NewsListViewModel::class.java]
    }

    @Provides
    fun provideSourcesAdapter() = NewsListAdapter(ArrayList())

    @Provides
    fun provideSearchViewModel(searchNewsRepository: SearchNewsRepository): SearchViewModel{
        return ViewModelProvider(activity,
            ViewModelProviderFactory(SearchViewModel::class) {
                SearchViewModel(searchNewsRepository)
            })[SearchViewModel::class.java]
    }

    @Provides
    fun provideSearchAdapter() = SearchViewAdapter(ArrayList())
}