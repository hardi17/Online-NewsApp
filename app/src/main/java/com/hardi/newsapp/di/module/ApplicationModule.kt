package com.hardi.newsapp.di.module

import android.content.Context
import com.hardi.NewsApplication
import com.hardi.newsapp.data.api.ApiKeyInterceptor
import com.hardi.newsapp.data.api.NetworkService
import com.hardi.newsapp.di.ApplicationContext
import com.hardi.newsapp.di.BaseUrl
import com.hardi.newsapp.di.NetworkApiKey
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: NewsApplication) {

    @ApplicationContext
    @Provides
    fun provideContext(): Context{
        return application
    }

    @BaseUrl
    @Provides
    fun provideBaseUrl() : String = "https://newsapi.org/v2/"

    @NetworkApiKey
    @Provides
    fun provideApiKey(): String = "c02ab86e9761496faebbf29d2c96fb06"

    @Provides
    @Singleton
    fun provideGsonConverterFactory() : GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideApiKeyInterceptor(@NetworkApiKey apiKey: String): ApiKeyInterceptor =
        ApiKeyInterceptor(apiKey)

    @Provides
    @Singleton
    fun provideOkHttpClient(apiKeyInterceptor: ApiKeyInterceptor): OkHttpClient =
        OkHttpClient().newBuilder().addInterceptor(apiKeyInterceptor).build()

    @Provides
    @Singleton
    fun provideNetworkService(
        @BaseUrl baseUrl: String,
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ) : NetworkService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(NetworkService::class.java)
    }
}