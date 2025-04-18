package com.example.moviemania.data.di

import com.example.moviemania.data.source.local.repository.LocalRepositoryImpl
import com.example.moviemania.data.source.remote.repository.RemoteRepositoryImpl
import com.example.moviemania.domain.repository.RemoteRepository
import com.example.moviemania.domain.repository.LocalRepository
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
    abstract fun bindMovieRepository(movieRepositoryImpl: RemoteRepositoryImpl): RemoteRepository

    @Binds
    @Singleton
    abstract fun bindWatchlistRepository(watchlistRepository: LocalRepositoryImpl): LocalRepository
}