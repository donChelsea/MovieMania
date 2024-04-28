package com.example.moviemania.ui.screens.watchlist

import androidx.lifecycle.viewModelScope
import com.example.moviemania.domain.models.Movie
import com.example.moviemania.domain.repository.WatchListRepository
import com.example.moviemania.ui.MovieManiaViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val repository: WatchListRepository,
) : MovieManiaViewModel<WatchlistUiState, WatchlistUiEvent, WatchlistUiAction>() {
    private val _state = MutableStateFlow(WatchlistUiState())
    override val state: StateFlow<WatchlistUiState>
        get() = _state.asStateFlow()

    init {
        initData()
    }

    override fun handleAction(action: WatchlistUiAction) {
        when (action) {
            WatchlistUiAction.OnNavigateBack -> viewModelScope.launch {
                _events.emit(WatchlistUiEvent.OnNavigateBack)
            }
            is WatchlistUiAction.OnMovieClicked -> viewModelScope.launch {
                _events.emit(WatchlistUiEvent.OnMovieClicked(action.movieId))
            }
        }
    }

    private fun initData() {
        viewModelScope.launch {
            repository.getSavedMovies().collectLatest { movies ->
                newUiState(ScreenData.Data(movies))
            }
        }
    }

    private fun saveMovie(movie: Movie) {
        viewModelScope.launch {
            repository.saveMovie(movie)
        }
    }

    private fun deleteMovie(movie: Movie) {
        viewModelScope.launch {
            repository.deleteMovie(movie)
        }
    }

    private fun newUiState(screenData: ScreenData) =
        _state.update { state -> state.copy(screenData = screenData) }
}
