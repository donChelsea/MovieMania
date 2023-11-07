package com.example.moviemania.ui.screens.details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.moviemania.domain.repository.MovieRepository
import com.example.moviemania.ui.navigation.Screen.MovieDetailArgs.MovieId
import com.example.moviemania.util.MovieManiaViewModel
import com.example.moviemania.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val repository: MovieRepository,
    savedStateHandle: SavedStateHandle,
) : MovieManiaViewModel<DetailsUiState, DetailsUiEvent, DetailsUiAction>() {
    private val _state = MutableStateFlow(DetailsUiState())
    override val state: StateFlow<DetailsUiState>
        get() = _state.asStateFlow()

    private val movieId = savedStateHandle[MovieId] ?: ""

    init {
        _state.update { it.copy(isLoading = true) }
        initData()
    }

    override fun handleAction(action: DetailsUiAction) {
        when (action) {
            DetailsUiAction.OnNavigateBack -> viewModelScope.launch { _events.emit(DetailsUiEvent.OnNavigateBack) }
        }
    }

    private fun initData() {
        viewModelScope.launch {
            repository.getMovieDetails(movieId).collectLatest { results ->
                when (results) {
                    is Resource.Success -> {
                        _state.update { it.copy(
                            movie = results.data,
                            isLoading = false,
                            isError = false)
                        }
                    }

                    is Resource.Error -> {
                        _state.update { it.copy(isError = true) }
                        Log.e("mm_test", "MovieDetailsViewModel: getMovieDetails: ${results.message.toString()}")
                    }

                    is Resource.Loading -> _state.update { it.copy(isLoading = true, isError = false) }
                }
            }
        }
    }
}
