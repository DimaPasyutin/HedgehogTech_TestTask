package com.example.hedgehogtech_testtask.data.network.retrofit

import com.example.hedgehogtech_testtask.data.network.models.MainResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface JokesApi {

    @GET("{count}")
    fun getAlbums(@Path("count") count: String): Single<MainResponse>

}