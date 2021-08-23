package com.example.quizapp.di.module

import com.example.quizapp.data.DefaultNetworkConfig
import com.example.quizapp.data.NetworkConfig
import com.example.quizapp.di.qualifiers.AuthRetrofit
import com.example.quizapp.di.qualifiers.QuestionRetrofit
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import com.google.gson.GsonBuilder

import com.google.gson.Gson




@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideHttpClient(
        networkConfig: NetworkConfig,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(networkConfig.connectTimeoutInMs, TimeUnit.SECONDS)
            writeTimeout(networkConfig.writeTimeoutInMs, TimeUnit.SECONDS)
            readTimeout(networkConfig.readTimeoutInMs, TimeUnit.SECONDS)
            addInterceptor(loggingInterceptor)
        }.build()
    }

    @Singleton
    @QuestionRetrofit
    @Provides
    fun provideQuestionRetrofit(
        okHttpClient: OkHttpClient,
        networkConfig: NetworkConfig
    ): Retrofit {
        return Retrofit.Builder().apply {
            addConverterFactory(GsonConverterFactory.create())
            client(okHttpClient)
            addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
            baseUrl(networkConfig.baseUrl)
        }.build()
    }

    @Singleton
    @AuthRetrofit
    @Provides
    fun provideAuthRetrofit(okHttpClient: OkHttpClient, networkConfig: NetworkConfig): Retrofit {
        return Retrofit.Builder().apply {
            addConverterFactory(GsonConverterFactory.create())
            client(okHttpClient)
            addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
            baseUrl(networkConfig.authUrl)
        }.build()
    }

    @Singleton
    @Provides
    fun provideNetworkConfig(): NetworkConfig = DefaultNetworkConfig()

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }
}
