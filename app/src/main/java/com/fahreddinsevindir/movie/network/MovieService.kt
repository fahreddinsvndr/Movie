package com.fahreddinsevindir.movie.network

import com.fahreddinsevindir.movie.model.Movies
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface MovieService {

    @GET("trending/all/day?api_key=f2923e6d299a67f0aec52f411b609dbc")
    fun getTrendingMovie(): Single<Movies>
}