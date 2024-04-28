package com.example.moviemania.ui.screens.details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.moviemania.domain.repository.MovieRepository
import com.example.moviemania.domain.repository.WatchlistRepository
import com.example.moviemania.ui.navigation.Screen.MovieDetailArgs.MovieId
import com.example.moviemania.ui.MovieManiaViewModel
import com.example.moviemania.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val movieRepository: MovieRepository,
    private val watchlistRepository: WatchlistRepository,
    savedStateHandle: SavedStateHandle,
) : MovieManiaViewModel<DetailsUiState, DetailsUiEvent, DetailsUiAction>() {
    private val _state = MutableStateFlow(DetailsUiState())
    override val state: StateFlow<DetailsUiState>
        get() = _state.asStateFlow()

    private val movieId = savedStateHandle[MovieId] ?: ""

    init {
        initData()
    }

    override fun handleAction(action: DetailsUiAction) {
        when (action) {
            DetailsUiAction.OnNavigateBack -> viewModelScope.launch { _events.emit(DetailsUiEvent.OnNavigateBack) }
            is DetailsUiAction.OnSaveToWatchlist -> {
                viewModelScope.launch {
                    watchlistRepository.getSavedMovies().collectLatest { saved ->
                        if (saved.isNotEmpty()) {
                            saved.forEach {
                                if (it.id == action.movie.id) {
                                    watchlistRepository.deleteMovie(action.movie)
                                    newUiState(ScreenData.Data(movie = action.movie, isSaved = false))
                                } else {
                                    watchlistRepository.saveMovie(action.movie)
                                    newUiState(ScreenData.Data(movie = action.movie, isSaved = true))
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun initData() {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.getMovieDetails(movieId).collectLatest { results ->
                when (results) {
                    is Resource.Success -> {
                        newUiState(ScreenData.Data(results.data))
                    }

                    is Resource.Error -> {
                        newUiState(ScreenData.Error)
                        Log.e(TAG, "getMovieDetails: ${results.message.toString()}")
                    }

                    else -> newUiState(ScreenData.Loading)
                }
            }
        }
    }

    private fun newUiState(screenData: ScreenData) =
        _state.update { state -> state.copy(screenData = screenData) }

    companion object {
        const val TAG = "MovieDetailsViewModel"
    }
}
