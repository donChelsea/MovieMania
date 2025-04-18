package com.example.moviemania.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviemania.data.local.model.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveMovie(movie: MovieEntity)

    @Query("SELECT * FROM movies")
    fun getSavedMoviesFlow(): Flow<List<MovieEntity>>

    @Delete
    suspend fun deleteMovie(movie: MovieEntity)
}