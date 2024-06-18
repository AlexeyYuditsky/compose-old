package com.alexeyyuditsky.vkclient.presentation.comments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alexeyyuditsky.vkclient.data.repository.CommentsRepository
import com.alexeyyuditsky.vkclient.domain.FeedPost
import kotlinx.coroutines.launch

class CommentsViewModel(
    application: Application,
    feedPost: FeedPost
) : AndroidViewModel(application) {

    private val _screenState = MutableLiveData<CommentsScreenState>(CommentsScreenState.Initial)
    val screenState: LiveData<CommentsScreenState> get() = _screenState

    private val repository = CommentsRepository(application)

    init {
        loadComments(feedPost)
    }

    private fun loadComments(feedPost: FeedPost) = viewModelScope.launch {
        val postCommentList = repository.getComments(feedPost)

        _screenState.value = CommentsScreenState.Comments(
            feedPost = feedPost,
            comments = postCommentList
        )
    }
}