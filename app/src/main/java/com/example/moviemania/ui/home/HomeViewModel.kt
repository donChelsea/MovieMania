package com.example.moviemania.ui.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.moviemania.domain.repository.MovieRepository
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
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository,
): MovieManiaViewModel<HomeUiState, HomeUiEvent, HomeUiAction>() {
    private val _state = MutableStateFlow(HomeUiState())
    override val state: StateFlow<HomeUiState>
        get() = _state.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getMovies().collectLatest { results ->
                when (results) {
                    is Resource.Success -> {
                        _state.update { it.copy(movies = results.data.orEmpty()) }
                        Log.e("HomeViewModel", results.data.toString())
                    }
                    is Resource.Error -> {
                        Log.e("HomeViewModel", results.message.toString())
                    }
                    is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    override fun handleAction(action: HomeUiAction) {

    }
}