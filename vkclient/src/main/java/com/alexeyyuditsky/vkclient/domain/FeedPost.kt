package com.alexeyyuditsky.vkclient.domain

import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.alexeyyuditsky.vkclient.core.getCheckedParcelable
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
data class FeedPost(
    val id: Long,
    val communityId:Long,
    val communityName: String,
    val publicationDate: String,
    val communityImageUrl: String,
    val contentText: String,
    val contentImageUrl: String?,
    val isLiked: Boolean,
    val statistics: List<StatisticItem>
) : Parcelable {
    fun encode(): String {
        val feedPostJson = Gson().toJson(this)
        return Uri.encode(feedPostJson)
    }
}

val NavType.Companion.FeedPostType: NavType<FeedPost>
    get() = object : NavType<FeedPost>(false) {

        override fun get(bundle: Bundle, key: String): FeedPost =
            bundle.getCheckedParcelable(key, FeedPost::class.java)

        override fun parseValue(value: String): FeedPost =
            Gson().fromJson(value, FeedPost::class.java)

        override fun put(bundle: Bundle, key: String, value: FeedPost) =
            bundle.putParcelable(key, value)
    }