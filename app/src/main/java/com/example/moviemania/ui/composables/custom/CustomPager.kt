@file:OptIn(ExperimentalFoundationApi::class)

package com.example.moviemania.ui.composables.custom

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moviemania.domain.models.Movie
import com.example.moviemania.ui.composables.cards.NowPlayingCard
import com.example.moviemania.ui.theme.Purple80
import com.google.accompanist.pager.HorizontalPagerIndicator

@Composable
fun CustomPager(
    data: List<Movie>,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = { data.size })
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.padding(bottom = 25.dp),
            contentPadding = PaddingValues(horizontal = 12.dp),
        ) { page ->
            NowPlayingCard(movie = data[page], onClick = { onClick(data[page].id) })
        }
        HorizontalPagerIndicator(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            pageCount = data.size,
            pagerState = pagerState,
            activeColor = MaterialTheme.colorScheme.primary
        )
    }
}