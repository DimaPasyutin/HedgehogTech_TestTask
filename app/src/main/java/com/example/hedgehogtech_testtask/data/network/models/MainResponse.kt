package com.example.hedgehogtech_testtask.data.network.models

import com.google.gson.annotations.SerializedName

data class MainResponse (

    @SerializedName("type")
    val type: String,

    @SerializedName("value")
    val jokeList: List<Joke>

)