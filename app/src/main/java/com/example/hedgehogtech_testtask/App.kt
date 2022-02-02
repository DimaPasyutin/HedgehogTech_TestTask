package com.example.hedgehogtech_testtask

import android.app.Application
import com.example.hedgehogtech_testtask.data.network.retrofit.JokesApi
import com.example.hedgehogtech_testtask.data.repository.JokesRepositoryImpl
import com.example.hedgehogtech_testtask.domain.JokesRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class App: Application() {

    lateinit var jokesApi: JokesApi

    lateinit var jokesRepository: JokesRepository

    override fun onCreate() {
        super.onCreate()
        configureRetrofit()

        jokesRepository = JokesRepositoryImpl(jokesApi = jokesApi)
    }

    private fun configureRetrofit() {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://api.icndb.com/jokes/random/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        jokesApi = retrofit.create(JokesApi::class.java)
    }

}