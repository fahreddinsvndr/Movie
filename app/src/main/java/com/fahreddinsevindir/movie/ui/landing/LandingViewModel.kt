package com.fahreddinsevindir.movie.ui.landing

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fahreddinsevindir.movie.model.Movie
import com.fahreddinsevindir.movie.model.Resource
import com.fahreddinsevindir.movie.repository.MovieRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import timber.log.Timber

class LandingViewModel @ViewModelInject constructor(
    movieRepository: MovieRepository
) : ViewModel() {

    private val _trendingMovies = MutableLiveData<Resource<List<Movie>>>()
    val trendingMovies: LiveData<Resource<List<Movie>>>
        get() = _trendingMovies

    init {
        movieRepository.getTrendingMovie()
            .doOnSubscribe { _trendingMovies.value = Resource.Loading(null) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { movies -> _trendingMovies.value = Resource.Success(movies.results) },
                { t ->
                    Timber.e(t)
                    _trendingMovies.value = Resource.Error(t.message!!, null)
                })
    }

}