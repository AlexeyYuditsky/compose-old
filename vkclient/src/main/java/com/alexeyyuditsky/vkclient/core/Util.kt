package com.alexeyyuditsky.vkclient.core

import android.os.Build
import android.os.Bundle
import android.os.Parcelable

fun <T> Bundle.getCheckedParcelable(key: String, clazz: Class<T>): T {
    @Suppress("UNCHECKED_CAST")
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelable(key, clazz) as T
    } else {
        @Suppress("DEPRECATION") getParcelable<Parcelable>(key) as T
    }
}