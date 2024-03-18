package com.alexeyyuditsky.vkclient.presentation.news

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alexeyyuditsky.vkclient.domain.FeedPost

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
        is NewsFeedScreenState.Posts -> FeedPostsScreen(
            paddingValues = paddingValues,
            feedPosts = state.posts,
            onCommentsClickListener = onCommentClickListener
        )
    }
}