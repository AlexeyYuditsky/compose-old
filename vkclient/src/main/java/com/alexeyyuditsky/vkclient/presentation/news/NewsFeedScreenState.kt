package com.alexeyyuditsky.vkclient.presentation.news

import com.alexeyyuditsky.vkclient.domain.FeedPost

sealed interface NewsFeedScreenState {

    object Initial : NewsFeedScreenState

    object Loading : NewsFeedScreenState

    class Posts(
        val posts: List<FeedPost>,
        val nextDataIsLoading: Boolean = false
    ) : NewsFeedScreenState
}