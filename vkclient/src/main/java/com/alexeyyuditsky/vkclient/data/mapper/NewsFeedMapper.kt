package com.alexeyyuditsky.vkclient.data.mapper

import com.alexeyyuditsky.vkclient.data.model.NewsFeedResponseDto
import com.alexeyyuditsky.vkclient.data.model.PostDto
import com.alexeyyuditsky.vkclient.domain.FeedPost
import com.alexeyyuditsky.vkclient.domain.StatisticItem
import com.alexeyyuditsky.vkclient.domain.StatisticType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.absoluteValue

class NewsFeedMapper {

    fun mapResponseToPosts(responseDto: NewsFeedResponseDto): List<FeedPost> {
        val result = mutableListOf<FeedPost>()

        val posts = responseDto.newsFeedContent.posts
        val groups = responseDto.newsFeedContent.groups

        posts.forEach { post ->
            val group = groups.find { group -> group.id == post.communityId.absoluteValue }
                ?: return@forEach

            val feedPost = FeedPost(
                id = post.id,
                communityId = post.communityId,
                communityName = group.name,
                publicationDate = mapTimestampToDate(post.date * 1000),
                communityImageUrl = group.imageUrl,
                contentText = post.text,
                contentImageUrl = post.getPhotoUrl(bestQuality = true),
                isLiked = post.likes.userLikes > 0,
                statistics = listOf(
                    StatisticItem(type = StatisticType.VIEWS, count = post.views.count),
                    StatisticItem(type = StatisticType.SHARES, count = post.reposts.count),
                    StatisticItem(type = StatisticType.COMMENTS, count = post.comments.count),
                    StatisticItem(type = StatisticType.LIKES, count = post.likes.count)
                )
            )

            result.add(feedPost)
        }

        return result
    }

    private fun mapTimestampToDate(timestamp: Long): String {
        val date = Date(timestamp)
        return SimpleDateFormat("d MMMM yyyy, hh:mm", Locale.getDefault()).format(date)
    }

    private fun PostDto.getPhotoUrl(bestQuality: Boolean): String? {
        val photoUrls = attachments?.firstOrNull()?.photo?.photoUrls ?: return null
        return if (bestQuality) {
            photoUrls.lastOrNull()?.url
        } else {
            photoUrls.firstOrNull()?.url
        }
    }
}