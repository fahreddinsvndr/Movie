package com.fahreddinsevindir.movie.ui.moviedetails

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.fahreddinsevindir.movie.model.Movie
import com.fahreddinsevindir.movie.model.Resource
import com.fahreddinsevindir.movie.repository.MovieRepository
import timber.log.Timber

class MovieDetailsViewModel @ViewModelInject constructor(
    private val movieRepository: MovieRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _movie = MutableLiveData<Resource<Movie>>()

    val movie: LiveData<Resource<Movie>>
        get() = _movie

    init {
        if (savedStateHandle.contains("movieId")) {
            val movieId = savedStateHandle.get<Long>("movieId")
            Timber.i("movieId: $movieId")
        } else {
            _movie.value = Resource.Error("OMG,unable to get the argument!!")
        }
    }

}