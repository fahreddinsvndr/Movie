package com.fahreddinsevindir.movie.repository

import com.fahreddinsevindir.movie.model.Movie
import com.fahreddinsevindir.movie.model.Movies
import com.fahreddinsevindir.movie.network.MovieService
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val movieService: MovieService
) {

    fun getTrendingMovie(): Single<Movies> {
        return movieService.getTrendingMovie()
            .subscribeOn(Schedulers.io())
    }

    fun getMovieDetails(movieId: Long): Single<Movie> {
        return movieService.getMovie(movieId)
            .subscribeOn(Schedulers.io())
    }
}