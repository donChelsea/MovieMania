package com.example.moviemania.ui.composables.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.moviemania.ui.composables.animations.ProgressAnimation

@Composable
fun ShowLoading() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        ProgressAnimation()
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewLoadingScreen() {
    ShowLoading()
}