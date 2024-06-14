package com.alexeyyuditsky.vkclient.presentation.news

import android.app.Application
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alexeyyuditsky.vkclient.data.repository.NewsFeedRepository
import com.alexeyyuditsky.vkclient.domain.FeedPost
import com.alexeyyuditsky.vkclient.domain.StatisticItem
import kotlinx.coroutines.launch

class NewsFeedViewModel(application: Application) : AndroidViewModel(application) {

    private val _screenState = MutableLiveData<NewsFeedScreenState>(NewsFeedScreenState.Initial)
    val screenState: LiveData<NewsFeedScreenState> get() = _screenState

    private val repository = NewsFeedRepository(application)

    init {
        _screenState.value = NewsFeedScreenState.Loading
        loadRecommendations()
    }

    fun changeLikeStatus(feedPost: FeedPost) = viewModelScope.launch {
        val feedPostList = repository.changeLikeStatus(feedPost)
        _screenState.value = NewsFeedScreenState.Posts(feedPostList)
    }

    private fun loadRecommendations() = viewModelScope.launch {
        val feedPostList = repository.loadRecommendations()
        _screenState.value = NewsFeedScreenState.Posts(feedPostList)
    }

    fun loadNextRecommendations() {
        _screenState.value = NewsFeedScreenState.Posts(
            posts = repository.feedPosts,
            nextDataIsLoading = true
        )
        loadRecommendations()
    }

    fun ignorePost(feedPost: FeedPost) = viewModelScope.launch {
        val feedPostList = repository.ignorePost(feedPost)
        _screenState.value = NewsFeedScreenState.Posts(feedPostList)
    }
}