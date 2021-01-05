package com.fahreddinsevindir.movie.network

import com.fahreddinsevindir.movie.model.Movie
import com.fahreddinsevindir.movie.model.Movies
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("trending/all/day")
    fun getTrendingMovie(@Query("page") page: Int): Single<Movies>

    @GET("movie/{movieId}?append_to_response=credits")
    fun getMovie(@Path("movieId") movieId: Long): Single<Movie>
}