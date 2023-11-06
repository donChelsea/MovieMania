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
) : MovieManiaViewModel<HomeUiState, HomeUiEvent, HomeUiAction>() {
    private val _state = MutableStateFlow(HomeUiState())
    override val state: StateFlow<HomeUiState>
        get() = _state.asStateFlow()

    init {
        initData()
    }

    override fun handleAction(action: HomeUiAction) {
        when (action) {
            HomeUiAction.OnWatchListClicked -> viewModelScope.launch { _events.emit(HomeUiEvent.OnWatchListClicked) }
        }
    }

    private fun initData() {
        viewModelScope.launch {
            repository.getNowPlaying().collectLatest { results ->
                when (results) {
                    is Resource.Success -> {
                        _state.update { it.copy(nowPlaying = results.data.orEmpty()) }
                        Log.e("mm_test", "HomeViewModel: getNowPlaying: ${_state.value.nowPlaying.toString()}")
                    }

                    is Resource.Error -> {
                        Log.e("mm_test", "HomeViewModel: getNowPlaying: ${results.message.toString()}")
                    }

                    is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                }
            }
        }

        viewModelScope.launch {
            repository.getTrending().collectLatest { results ->
                when (results) {
                    is Resource.Success -> {
                        _state.update { it.copy(trending = results.data.orEmpty()) }
                    }

                    is Resource.Error -> {
                        Log.e("mm_test", "HomeViewModel: getTrending: ${results.message.toString()}")
                    }

                    is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                }
            }
        }

        viewModelScope.launch {
            repository.getUpcoming().collectLatest { results ->
                when (results) {
                    is Resource.Success -> {
                        _state.update { it.copy(upcoming = results.data.orEmpty()) }
                    }

                    is Resource.Error -> {
                        Log.e("mm_test", "HomeViewModel: getUpcoming: ${results.message.toString()}")
                    }

                    is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                }
            }
        }

        viewModelScope.launch {
            repository.getGenres().collectLatest { results ->
                when (results) {
                    is Resource.Success -> {
                        _state.update { it.copy(genres = results.data.orEmpty()) }
                    }

                    is Resource.Error -> {
                        Log.e("mm_test", "HomeViewModel: getGenres: ${results.message.toString()}")
                    }

                    is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                }
            }
        }
    }
}
