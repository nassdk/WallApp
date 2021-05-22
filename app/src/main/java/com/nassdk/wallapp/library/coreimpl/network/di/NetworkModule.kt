package com.nassdk.wallapp.library.coreimpl.network.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nassdk.wallapp.BuildConfig
import com.nassdk.wallapp.library.coreapi.network.ApiService
import com.nassdk.wallapp.library.coreimpl.network.connection.NetworkChecking
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    companion object {

        @Provides
        @Singleton
        fun provideLoggingInterceptor() = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        @Provides
        @Singleton
        fun provideOkHttp3(
            loggingInterceptor: HttpLoggingInterceptor
        ): OkHttpClient =
            OkHttpClient.Builder().apply {
                if (BuildConfig.DEBUG) addInterceptor(loggingInterceptor)
            }.build()


        @Provides
        @Singleton
        fun provideGson(): Gson = GsonBuilder()
            .setLenient()
            .create()

        @Provides
        @Singleton
        fun provideNetworkChecker(context: Context): NetworkChecking =
            NetworkChecking(context = context)

        @Provides
        @Singleton
        fun provideApiService(retrofit: Retrofit): ApiService =
            retrofit.create(ApiService::class.java)


        @Provides
        @Singleton
        fun provideRetrofit(
            client: OkHttpClient,
            gson: Gson
        ): Retrofit =
            Retrofit.Builder()
                .baseUrl("https://api.vk.com/method/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }
}