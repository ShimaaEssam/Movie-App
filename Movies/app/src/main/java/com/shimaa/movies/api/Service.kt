package com.shimaa.movies.api

import com.shimaa.movies.model.moviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

abstract class Service {
    //34an ageb data mn l websservice l fl background badeha l api
    @GET("movie/popular")
    abstract fun getPopularMovies(@Query("api_key")apiKey:String ): Call<moviesResponse>

    @GET("movie/top_rated")
    abstract fun getTopRatedMovies(@Query("api_key")apiKey:String ): Call<moviesResponse>


}