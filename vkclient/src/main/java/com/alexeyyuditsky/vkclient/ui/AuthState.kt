package com.alexeyyuditsky.vkclient.ui

sealed interface AuthState {
    object Initial : AuthState
    object Authorized : AuthState
    object NotAuthorized : AuthState
}