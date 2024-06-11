package com.alexeyyuditsky.vkclient.data.repository

import android.app.Application
import com.alexeyyuditsky.vkclient.data.mapper.NewsFeedMapper
import com.alexeyyuditsky.vkclient.data.network.ApiFactory
import com.alexeyyuditsky.vkclient.domain.FeedPost
import com.alexeyyuditsky.vkclient.domain.StatisticType
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken

class NewsFeedRepository(
    application: Application
) {
    private val storage = VKPreferencesKeyValueStorage(application)
    private val token = VKAccessToken.restore(storage)

    private val apiService = ApiFactory.apiService
    private val mapper = NewsFeedMapper()

    private val feedPostList = mutableListOf<FeedPost>()

    suspend fun loadRecommendations(): List<FeedPost> {
        val response = apiService.loadRecommendations(token = getAccessToken())
        val feedPostList = mapper.mapResponseToPosts(response)
        this.feedPostList.clear()
        this.feedPostList.addAll(feedPostList)
        return this.feedPostList
    }

    suspend fun changeLikeStatus(feedPost: FeedPost): List<FeedPost> {
        val response = if (feedPost.isLiked) {
            apiService.deleteLike(
                token = getAccessToken(),
                ownerId = feedPost.communityId,
                postId = feedPost.id
            )
        } else {
            apiService.addLike(
                token = getAccessToken(),
                ownerId = feedPost.communityId,
                postId = feedPost.id
            )
        }
        val newLikesCount = response.likes.count

        val newStatistics = feedPost.statistics.map { statisticItem ->
            if (statisticItem.type == StatisticType.LIKES) {
                statisticItem.copy(count = newLikesCount)
            } else {
                statisticItem
            }
        }

        val newPost = feedPost.copy(statistics = newStatistics, isLiked = !feedPost.isLiked)
        val feedPostIndex = this.feedPostList.indexOf(feedPost)
        this.feedPostList[feedPostIndex] = newPost

        return this.feedPostList
    }

    private fun getAccessToken(): String {
        return token?.accessToken ?: throw IllegalStateException("Token is null")
    }
}