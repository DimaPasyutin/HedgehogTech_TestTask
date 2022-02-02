package com.example.hedgehogtech_testtask.domain

import com.example.hedgehogtech_testtask.data.network.models.Joke
import io.reactivex.Single

interface JokesRepository {

    fun loadJokes(count: String): Single<List<Joke>>

    fun saveAlbumsInMemoryCache(newJokes: List<Joke>)

}