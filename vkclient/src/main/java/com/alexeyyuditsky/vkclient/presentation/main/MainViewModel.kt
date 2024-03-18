package com.alexeyyuditsky.vkclient.presentation.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alexeyyuditsky.vkclient.core.log
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthenticationResult

class MainViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val _authState = MutableLiveData<AuthState>(AuthState.Initial)
    val authState: LiveData<AuthState> get() = _authState

    init {
        val storage = VKPreferencesKeyValueStorage(application)
        val token = VKAccessToken.restore(storage)
        log(token?.accessToken)
        val loggedIn = token != null && token.isValid
        _authState.value = if (loggedIn) AuthState.Authorized else AuthState.NotAuthorized
    }

    fun performAuthResult(result: VKAuthenticationResult) {
        _authState.value = when (result) {
            is VKAuthenticationResult.Success -> AuthState.Authorized
            is VKAuthenticationResult.Failed -> AuthState.NotAuthorized
        }
    }
}