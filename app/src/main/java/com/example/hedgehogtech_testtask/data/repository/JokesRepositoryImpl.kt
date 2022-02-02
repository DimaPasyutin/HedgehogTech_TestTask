package com.example.hedgehogtech_testtask.data.repository

import com.example.hedgehogtech_testtask.data.network.models.Joke
import com.example.hedgehogtech_testtask.data.network.retrofit.JokesApi
import com.example.hedgehogtech_testtask.domain.JokesRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class JokesRepositoryImpl(private val jokesApi: JokesApi): JokesRepository {

    @Volatile
    var jokesInMemoryCache: List<Joke> = ArrayList()

    override fun loadJokes(count: String): Single<List<Joke>> {
        return jokesApi.getAlbums(count)
            .map { it.jokeList }
            .doOnSuccess { saveAlbumsInMemoryCache(it) }
            .subscribeOn(Schedulers.io())
    }

    override fun saveAlbumsInMemoryCache(newJokes: List<Joke>) {
        if (jokesInMemoryCache == newJokes) return
        jokesInMemoryCache = newJokes
    }
}