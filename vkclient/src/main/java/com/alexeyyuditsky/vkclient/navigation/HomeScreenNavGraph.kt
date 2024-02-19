package com.alexeyyuditsky.vkclient.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.alexeyyuditsky.vkclient.core.getCheckedParcelable
import com.alexeyyuditsky.vkclient.domain.FeedPost
import com.alexeyyuditsky.vkclient.domain.FeedPostType

fun NavGraphBuilder.homeScreenNavGraph(
    newsFeedScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (FeedPost) -> Unit
) {
    navigation(
        startDestination = Screen.NewsFeed.route,
        route = Screen.Home.route
    ) {
        composable(Screen.NewsFeed.route) {
            newsFeedScreenContent()
        }
        composable(
            route = Screen.Comments.route,
            arguments = listOf(
                navArgument(Screen.KEY_FEED_POST) {
                    type = NavType.FeedPostType
                }
            )
        ) { navBackStackEntry ->
            val feedPost = navBackStackEntry.arguments?.getCheckedParcelable(
                Screen.KEY_FEED_POST,
                FeedPost::class.java
            ) ?: throw RuntimeException("args is null")

            commentsScreenContent(feedPost)
        }
    }
}