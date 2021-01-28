package com.joker.lpgo.mobile.data.remote

import android.content.Context
import com.ashokvarma.gander.GanderInterceptor
import com.joker.lpgo.mobile.BuildConfig
import com.joker.lpgo.mobile.data.preference.AppPreference
import com.joker.lpgo.mobile.util.AppConstants
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit

object AppApi {

    fun getRequest(baseUrl: String = "", withBaseURL: Boolean = true, withTokenAuth: Boolean = false, context: Context? = null): Retrofit? {
        return getRetrofitBuilder(baseUrl = baseUrl, withBaseURL = withBaseURL, withTokenAuth = withTokenAuth, context = context)?.build()
    }

    private fun getBaseUrl(): String {
        return BuildConfig.BASE_URL
    }

    private fun getRetrofitBuilder(baseUrl: String = "", withBaseURL: Boolean = false, withTokenAuth: Boolean = false, context: Context? = null): Retrofit.Builder? {
        try {
            val retrofitBuilder = Retrofit.Builder()
            val httpClient = OkHttpClient.Builder()

            httpClient.addInterceptor { chain ->
                val builder = chain.request().newBuilder()
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded")
                if (withTokenAuth) {
                    builder.addHeader("Authorization", "Bearer ".plus(AppPreference.getAppToken()))
                }
                val request = builder.build()
                chain.proceed(request)
            }

            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(interceptor)

            if (BuildConfig.DEBUG) {
                context?.let {
                    httpClient.addInterceptor(
                        GanderInterceptor(it)
                            .showNotification(true)
                            .maxContentLength(250000L)
                            .retainDataFor(GanderInterceptor.Period.FOREVER)
                            .redactHeader("Authorization")
                    )
                }
            }

            httpClient.connectTimeout(AppConstants.APP_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(AppConstants.APP_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(AppConstants.APP_READ_TIMEOUT, TimeUnit.SECONDS)

            retrofitBuilder.client(httpClient.build())

            if(withBaseURL) {
                retrofitBuilder.baseUrl(getBaseUrl())
            } else {
                retrofitBuilder.baseUrl(baseUrl)
            }

            retrofitBuilder.addCallAdapterFactory(
                RxJava2CallAdapterFactory.createWithScheduler(
                    Schedulers.io()
                )
            )
            retrofitBuilder.addConverterFactory(GsonConverterFactory.create())
            return retrofitBuilder
        } catch (exception: Throwable) {
            exception.printStackTrace()
            return null
        } catch (exception: SocketTimeoutException) {
            exception.printStackTrace()
            return null
        }
    }
}