package com.example.moviemania.di

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import com.example.moviemania.data.local.MovieDao
import com.example.moviemania.data.local.MovieDatabase
import com.example.moviemania.data.remote.MovieApi
import com.example.moviemania.data.remote.MovieRepositoryImpl
import com.example.moviemania.domain.repository.MovieRepository
import com.example.moviemania.util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
private object AppModule {

    @Provides
    @Singleton
    fun provideMovieApi(): MovieApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApi::class.java)

    @Provides
    @Singleton
    fun provideMovieRepository(api: MovieApi): MovieRepository = MovieRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideMovieDatabase(app: Application): MovieDatabase =
        Room.databaseBuilder(
            app,
            MovieDatabase::class.java,
            "movie_database"
        ).build()

    @Provides
    @Singleton
    fun provideMovieDao(database: MovieDatabase): MovieDao = database.movieDao()
}