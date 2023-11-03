@file:OptIn(ExperimentalFoundationApi::class)

package com.example.moviemania.ui.common

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moviemania.domain.models.Movie
import com.google.accompanist.pager.HorizontalPagerIndicator

@Composable
fun MovieManiaPager(
    data: List<Movie>,
) {
    val pagerState = rememberPagerState(pageCount = { data.size })
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.padding(bottom = 25.dp),
        ) { page ->
            MovieManiaNowPlayingCard(movie = data[page], onClick = { })
        }
        HorizontalPagerIndicator(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            pageCount = data.size,
            pagerState = pagerState,
        )
    }
}