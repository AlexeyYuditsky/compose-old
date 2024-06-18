package com.alexeyyuditsky.vkclient.data.repository

import android.app.Application
import com.alexeyyuditsky.vkclient.data.mapper.NewsFeedMapper
import com.alexeyyuditsky.vkclient.data.network.ApiFactory
import com.alexeyyuditsky.vkclient.data.network.ApiService
import com.alexeyyuditsky.vkclient.domain.FeedPost
import com.alexeyyuditsky.vkclient.domain.PostComment

class CommentsRepository(
    application: Application,
    private val api: ApiService = ApiFactory.apiService
) : BaseRepository(application) {

    private val newsFeedMapper = NewsFeedMapper()

    suspend fun getComments(feedPost: FeedPost): List<PostComment> {
        val response = api.getComments(
            ownerId = feedPost.communityId,
            postId = feedPost.id,
            token = getAccessToken()
        )

        val commentPostList = newsFeedMapper.mapResponseToComments(response)
        return commentPostList
    }

}