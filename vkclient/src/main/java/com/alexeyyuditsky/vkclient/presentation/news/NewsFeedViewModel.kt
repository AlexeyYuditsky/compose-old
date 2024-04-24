package com.alexeyyuditsky.vkclient.presentation.news

import android.app.Application
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexeyyuditsky.vkclient.data.mapper.NewsFeedMapper
import com.alexeyyuditsky.vkclient.data.network.ApiFactory
import com.alexeyyuditsky.vkclient.domain.FeedPost
import com.alexeyyuditsky.vkclient.domain.StatisticItem
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.launch

class NewsFeedViewModel(application: Application) : AndroidViewModel(application) {

    private val _screenState = MutableLiveData<NewsFeedScreenState>(NewsFeedScreenState.Initial)
    val screenState: LiveData<NewsFeedScreenState> get() = _screenState

    private val mapper = NewsFeedMapper()

    init {
        loadRecommendations()
    }

    private fun loadRecommendations() {
        viewModelScope.launch {
            val storage = VKPreferencesKeyValueStorage(getApplication())
            val token = VKAccessToken.restore(storage) ?: return@launch
            val response = ApiFactory.apiService.loadRecommendations(token.accessToken)
            val feedPosts = mapper.mapResponseToPosts(response)
            _screenState.value = NewsFeedScreenState.Posts(feedPosts)
        }
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