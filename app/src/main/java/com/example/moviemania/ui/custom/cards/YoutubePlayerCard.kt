package com.example.moviemania.ui.custom.cards

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import com.example.moviemania.domain.model.VideoUiModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun YoutubePlayerCard(
    modifier: Modifier = Modifier,
    videoUiModel: VideoUiModel,
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
            YoutubePlayer(videoKey = videoUiModel.key, lifecycleOwner = lifecycleOwner)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = videoUiModel.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 34.dp)
            )
        }
    }
}

@Composable
private fun YoutubePlayer(
    modifier: Modifier = Modifier,
    videoKey: String,
    lifecycleOwner: LifecycleOwner,
) {
    AndroidView(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp)),
        factory = { context ->
            YouTubePlayerView(context = context).apply {
                lifecycleOwner.lifecycle.addObserver(this)

                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.loadVideo(
                            videoId = videoKey,
                            startSeconds = 0f
                        )
                    }
                })
            }
        }
    )
}