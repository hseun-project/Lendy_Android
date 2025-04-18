package com.hseun.lendy.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiProvider {
//    private const val BASE_URL = "http://192.168.1.20:8080"
    private const val BASE_URL = "http://10.0.2.2:8080"

    @Singleton
    private val instance: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun getInstance(): Retrofit {
        return instance
    }

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideOpenApi(retrofit: Retrofit): OpenApi {
        return retrofit.create(OpenApi::class.java)
    }
}