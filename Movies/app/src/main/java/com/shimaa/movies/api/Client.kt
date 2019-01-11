package com.shimaa.movies.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Client {
    val Base_URL = "http://api.themoviedb.org/3/"

    var retrofit: Retrofit? = null//34an ageb data mn l websservice l fl background w badeha l api
    fun getClient(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder().baseUrl(Base_URL).addConverterFactory(GsonConverterFactory.create()).build()
        }
        return retrofit
    }
}