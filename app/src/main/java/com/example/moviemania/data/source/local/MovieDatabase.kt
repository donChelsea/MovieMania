package com.example.moviemania.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviemania.data.source.local.model.MovieEntity

@Database(entities = [(MovieEntity::class)], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun dao(): MovieDao
}