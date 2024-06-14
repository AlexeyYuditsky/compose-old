package com.alexeyyuditsky.vkclient.presentation.news

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alexeyyuditsky.vkclient.domain.FeedPost
import com.alexeyyuditsky.vkclient.ui.theme.DarkBlue

@Composable
fun NewsFeedScreen(
    paddingValues: PaddingValues,
    onCommentClickListener: (FeedPost) -> Unit
) {
    val viewModel = viewModel<NewsFeedViewModel>()

    val screenState: State<NewsFeedScreenState> =
        viewModel.screenState.observeAsState(NewsFeedScreenState.Initial)

    when (val state = screenState.value) {
        is NewsFeedScreenState.Initial -> {}
        is NewsFeedScreenState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = DarkBlue)
            }
        }

        is NewsFeedScreenState.Posts -> {
            FeedPostsScreen(
                paddingValues = paddingValues,
                feedPosts = state.posts,
                nextDataIsLoading = state.nextDataIsLoading,
                onCommentsClickListener = onCommentClickListener
            )
        }
    }
}