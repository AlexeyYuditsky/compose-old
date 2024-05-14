package com.alexeyyuditsky.vkclient.core

import android.util.Log

fun <T> logger(message: T) {
    Log.d("MyLogger", message.toString())
}