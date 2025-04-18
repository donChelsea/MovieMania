package com.example.moviemania.ui.custom.states

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.moviemania.R

@Composable
fun ShowOffline() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(id = R.string.offline_screen),
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewOfflineScreen() {
    ShowOffline()
}

