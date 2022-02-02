package com.example.hedgehogtech_testtask.data.network.models
import com.google.gson.annotations.SerializedName

data class Joke (

    @SerializedName("id")
    var id: Long,

    @SerializedName("joke")
    var joke_text: String,

    @SerializedName("categories")
    var categories: List<Any>? = null

)