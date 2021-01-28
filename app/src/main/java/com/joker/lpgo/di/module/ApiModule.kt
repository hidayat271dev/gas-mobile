package com.joker.lpgo.di.module

import android.content.Context
import com.ashokvarma.gander.GanderInterceptor
import com.joker.lpgo.BuildConfig
import com.joker.lpgo.data.api.ApiHelper
import com.joker.lpgo.data.api.ApiHelperImpl
import com.joker.lpgo.data.api.ApiService
import com.joker.lpgo.data.sharepreference.AppSharedPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ApiModule {

    @Provides
    fun provideApiBaseUrl() = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideApiOkHttpClient(@ApplicationContext context: Context) = if (BuildConfig.DEBUG) {
        val okHttpClient = OkHttpClient.Builder()

        okHttpClient.addInterceptor(
            GanderInterceptor(context)
                .showNotification(true)
                .maxContentLength(250000L)
                .retainDataFor(GanderInterceptor.Period.FOREVER)
                .redactHeader("Authorization")
        )

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        okHttpClient.addInterceptor(loggingInterceptor)

        okHttpClient.addInterceptor { chain ->
            val builder = chain.request().newBuilder()
            builder.addHeader("Content-Type", "application/x-www-form-urlencoded")
            AppSharedPreference.instance.appToken().let { builder.addHeader("x-access-token", it) }
            val request = builder.build()
            chain.proceed(request)
        }

        okHttpClient.connectTimeout(50, TimeUnit.SECONDS)
            .writeTimeout(50, TimeUnit.SECONDS)
            .readTimeout(50, TimeUnit.SECONDS)

        okHttpClient.build()

    } else {
        val okHttpClient = OkHttpClient.Builder()

        okHttpClient.addInterceptor { chain ->
            val builder = chain.request().newBuilder()
            builder.addHeader("Content-Type", "application/x-www-form-urlencoded")
            AppSharedPreference.instance.appToken().let { builder.addHeader("x-access-token", it) }
            val request = builder.build()
            chain.proceed(request)
        }

        okHttpClient.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper


}