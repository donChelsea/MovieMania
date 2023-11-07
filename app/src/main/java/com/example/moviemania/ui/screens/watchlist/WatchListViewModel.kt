package com.example.moviemania.ui.screens.watchlist

import androidx.lifecycle.viewModelScope
import com.example.moviemania.domain.models.Movie
import com.example.moviemania.domain.repository.WatchListRepository
import com.example.moviemania.util.MovieManiaViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchListViewModel @Inject constructor(
    private val repository: WatchListRepository,
) : MovieManiaViewModel<WatchListUiState, WatchListUiEvent, WatchListUiAction>() {
    private val _state = MutableStateFlow(WatchListUiState())
    override val state: StateFlow<WatchListUiState>
        get() = _state.asStateFlow()

    init {
        initData()
    }

    override fun handleAction(action: WatchListUiAction) {
        when (action) {
            WatchListUiAction.OnNavigateBack -> viewModelScope.launch { _events.emit(WatchListUiEvent.OnNavigateBack) }
            is WatchListUiAction.OnMovieClicked -> viewModelScope.launch { _events.emit(WatchListUiEvent.OnMovieClicked(action.movieId)) }
        }
    }

    private fun initData() {
        viewModelScope.launch {
            repository.getSavedMovies().collectLatest { movies ->
                _state.update { it.copy(movies = movies) }
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
}
