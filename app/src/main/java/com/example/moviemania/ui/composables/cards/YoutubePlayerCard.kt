package com.example.moviemania.ui.composables.cards

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import com.example.moviemania.domain.models.Video
import com.example.moviemania.ui.composables.custom.YoutubePlayer

@Composable
fun YoutubePlayerCard(
    modifier: Modifier = Modifier,
    video: Video,
    lifecycleOwner: LifecycleOwner,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground
        )
    ) {
        Column(modifier = modifier.fillMaxWidth()) {
            YoutubePlayer(videoKey = video.key, lifecycleOwner = lifecycleOwner)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = video.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 34.dp)
            )
        }
    }
}
