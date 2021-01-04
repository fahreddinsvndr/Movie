package com.fahreddinsevindir.movie.ui.landing


import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.fahreddinsevindir.movie.R
import com.fahreddinsevindir.movie.model.Status
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_landing.*
import kotlinx.android.synthetic.main.layout_error.*
import kotlinx.android.synthetic.main.layout_loading.*
import timber.log.Timber

@AndroidEntryPoint
class LandingFragment : Fragment(R.layout.fragment_landing) {

    private lateinit var movieAdapter: MovieAdapter

    private val landingViewModel by viewModels<LandingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieAdapter = MovieAdapter()
        rvMovie.layoutManager = LinearLayoutManager(requireContext())
        rvMovie.adapter = movieAdapter.withLoadStateFooter(
            MovieFooterStateAdapter {
                movieAdapter.retry()
            }
        )

        movieAdapter.addLoadStateListener { loadState ->
            srl.isRefreshing = loadState.refresh is LoadState.Loading
            llErrorContainer.isVisible = loadState.source.refresh is LoadState.Error
            rvMovie.isVisible = !llErrorContainer.isVisible

            if (loadState.source.refresh is LoadState.Error){
                btnRetry.setOnClickListener {
                    movieAdapter.retry()
                }

                llErrorContainer.isVisible = loadState.source.refresh is LoadState.Error
                val errorMessage = (loadState.source.refresh as LoadState.Error).error.message
                tvErrorMessage.text = errorMessage
            }
            srl.setOnRefreshListener {
                landingViewModel.onRefresh()
            }
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        landingViewModel.trendingMovies.observe(viewLifecycleOwner, Observer {
            movieAdapter.submitData(lifecycle, it)

        })
    }


}