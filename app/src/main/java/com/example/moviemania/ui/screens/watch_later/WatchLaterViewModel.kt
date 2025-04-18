package com.example.moviemania.ui.screens.watch_later

import com.example.moviemania.domain.use_case.DeleteMovieUseCase
import com.example.moviemania.domain.use_case.GetSavedMoviesUseCase
import com.example.moviemania.ui.common.BaseViewModel
import com.example.moviemania.ui.model.MovieUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class WatchLaterViewModel @Inject constructor(
    private val getSavedMoviesUseCase: GetSavedMoviesUseCase,
    private val deleteMovieUseCase: DeleteMovieUseCase,
) : BaseViewModel<WatchLaterUiState, WatchLaterUiEvent, WatchLaterUiAction>() {
    private val _state = MutableStateFlow(WatchLaterUiState())

    override val state: StateFlow<WatchLaterUiState>
        get() = _state.asStateFlow()

    init {
        initData()
    }

    override fun handleAction(action: WatchLaterUiAction) {
        when (action) {
            is WatchLaterUiAction.OnMovieClicked -> safeLaunch {
                _events.emit(WatchLaterUiEvent.OnMovieClicked(action.movieId))
            }
            is WatchLaterUiAction.OnDeleteMovie -> deleteMovie(action.movieUiModel)
        }
    }

    private fun initData() {
        safeLaunch {
            getSavedMoviesUseCase().collectLatest { movies ->
                newUiState(ScreenData.Data(movies.map { it.toUiModel() }.asReversed()))
            }
        }
    }

    private fun deleteMovie(movie: MovieUiModel) {
        safeLaunch {
            deleteMovieUseCase(movie.toEntity())
        }
    }

    private fun newUiState(screenData: ScreenData) =
        _state.update { state -> state.copy(screenData = screenData) }
}
