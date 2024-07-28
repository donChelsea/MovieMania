package com.example.moviemania.ui.screens.watch_later

import androidx.lifecycle.viewModelScope
import com.example.moviemania.domain.models.Movie
import com.example.moviemania.domain.repository.WatchLaterRepository
import com.example.moviemania.ui.MovieManiaViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchLaterViewModel @Inject constructor(
    private val repository: WatchLaterRepository,
) : MovieManiaViewModel<WatchLaterUiState, WatchLaterUiEvent, WatchLaterUiAction>() {
    private val _state = MutableStateFlow(WatchLaterUiState())

    override val state: StateFlow<WatchLaterUiState>
        get() = _state.asStateFlow()

    init {
        initData()
    }

    override fun handleAction(action: WatchLaterUiAction) {
        when (action) {
            is WatchLaterUiAction.OnMovieClicked -> viewModelScope.launch {
                _events.emit(WatchLaterUiEvent.OnMovieClicked(action.movieId))
            }
            is WatchLaterUiAction.onDeleteMovie -> deleteMovie(action.movie)
        }
    }

    private fun initData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getSavedMovies().collectLatest { movies ->
                newUiState(ScreenData.Data(movies))
            }
        }
    }

    private fun deleteMovie(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMovie(movie)
        }
    }

    private fun newUiState(screenData: ScreenData) =
        _state.update { state -> state.copy(screenData = screenData) }
}
