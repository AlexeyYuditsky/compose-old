package com.alexeyyuditsky.vkclient.data.network

import com.alexeyyuditsky.vkclient.data.model.CommentsResponseDto
import com.alexeyyuditsky.vkclient.data.model.LikesCountResponseDto
import com.alexeyyuditsky.vkclient.data.model.NewsFeedResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("newsfeed.getRecommended?v=$VERSION")
    suspend fun loadRecommendations(
        @Query("access_token") token: String
    ): NewsFeedResponseDto

    @GET("newsfeed.getRecommended?v=$VERSION")
    suspend fun loadRecommendations(
        @Query("access_token") token: String,
        @Query("start_from") nextFrom: String
    ): NewsFeedResponseDto

    @GET("newsfeed.ignoreItem?type=wall&v=$VERSION")
    suspend fun ignorePost(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") postId: Long,
    )

    @GET("likes.add?type=post&v=$VERSION")
    suspend fun addLike(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") postId: Long
    ): LikesCountResponseDto

    @GET("likes.delete?type=post&v=$VERSION")
    suspend fun deleteLike(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") postId: Long
    ): LikesCountResponseDto

    @GET("wall.getComments?extended=1&fields=photo_100&v=$VERSION")
    suspend fun getComments(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("post_id") postId: Long
    ): CommentsResponseDto

    private companion object {
        const val VERSION = 5.199
    }
}