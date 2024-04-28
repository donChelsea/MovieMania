package com.example.moviemania.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.moviemania.ui.navigation.MovieManiaNavHost
import com.example.moviemania.ui.theme.MovieManiaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieManiaTheme {
                MovieManiaNavHost()
            }
        }
    }
}