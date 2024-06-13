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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NewsFeedViewModel(application: Application) : AndroidViewModel(application) {

    private val _screenState = MutableLiveData<NewsFeedScreenState>(NewsFeedScreenState.Initial)
    val screenState: LiveData<NewsFeedScreenState> get() = _screenState

    private val repository = NewsFeedRepository(application)

    init {
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

    fun updateCount(feedPost: FeedPost, statisticItem: StatisticItem) {
        val state = screenState.value
        if (state !is NewsFeedScreenState.Posts) return

        val oldFeedPostList = state.posts.toMutableList()

        val newStatistics = feedPost.statistics.map {
            if (statisticItem.type == it.type) {
                it.copy(count = it.count + 1)
            } else {
                it
            }
        }

        val newFeedPost = feedPost.copy(statistics = newStatistics)

        val newPosts = oldFeedPostList.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                replaceAll {
                    if (feedPost.id == it.id) {
                        newFeedPost
                    } else {
                        it
                    }
                }
            } else {
                oldFeedPostList.forEachIndexed { index, it ->
                    if (feedPost.id == it.id) {
                        oldFeedPostList[index] = newFeedPost
                    }
                }
            }
        }

        _screenState.value = NewsFeedScreenState.Posts(posts = newPosts)
    }

    fun remove(feedPost: FeedPost) {
        val state = screenState.value
        if (state !is NewsFeedScreenState.Posts) return

        val newPosts = state.posts.filter { it != feedPost }
        _screenState.value = NewsFeedScreenState.Posts(newPosts)
    }
}