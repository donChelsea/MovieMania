package com.example.moviemania.di

import com.example.moviemania.data.local.repository.WatchLaterRepositoryImpl
import com.example.moviemania.data.remote.repository.MovieRepositoryImpl
import com.example.moviemania.domain.repository.MovieRepository
import com.example.moviemania.domain.repository.WatchLaterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMovieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository

    @Binds
    @Singleton
    abstract fun bindWatchlistRepository(watchlistRepository: WatchLaterRepositoryImpl): WatchLaterRepository
}