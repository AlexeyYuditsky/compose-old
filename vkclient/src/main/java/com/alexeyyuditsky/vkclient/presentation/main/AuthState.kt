package com.alexeyyuditsky.vkclient.presentation.main

sealed interface AuthState {
    object Initial : AuthState
    object Authorized : AuthState
    object NotAuthorized : AuthState
}