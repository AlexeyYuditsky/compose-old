package com.alexeyyuditsky.vkclient.data.network

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

    @GET("likes.add?v=$VERSION&type=post")
    suspend fun addLike(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") postId: Long
    ): LikesCountResponseDto

    @GET("likes.delete?v=$VERSION&type=post")
    suspend fun deleteLike(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") postId: Long
    ): LikesCountResponseDto

    private companion object {
        const val VERSION = 5.199
    }
}