package com.example.moviemania.ui.composables.bookmark

import androidx.compose.foundation.layout.size
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BookmarkButtonView(
    state: BookmarkState,
    modifier: Modifier = Modifier,
    onBookmarkClicked: () -> Unit,
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onBookmarkClicked,
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = MaterialTheme.colors.primaryVariant,
            contentColor = Color.White
        ),
        enabled = state != BookmarkState.Toggling,
        content = {
            when (state) {
                BookmarkState.Toggling -> CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 2.dp
                )

                BookmarkState.Bookmarked -> Icon(
                    imageVector = Icons.Filled.Bookmark,
                    contentDescription = ""
                )

                BookmarkState.NotBookmarked -> Icon(
                    imageVector = Icons.Outlined.BookmarkAdd,
                    contentDescription = ""
                )
            }
        })
}

sealed interface BookmarkState {
    object Bookmarked : BookmarkState
    object NotBookmarked : BookmarkState
    object Toggling : BookmarkState
}