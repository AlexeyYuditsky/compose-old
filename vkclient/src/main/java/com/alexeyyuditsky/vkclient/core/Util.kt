package com.alexeyyuditsky.vkclient.core

import android.os.Build
import android.os.Bundle
import android.os.Parcelable

@Suppress("UNCHECKED_CAST")
fun <T> Bundle.getCheckedParcelable(key: String, clazz: Class<T>): T =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelable(key, clazz) as T
    } else {
        @Suppress("DEPRECATION") getParcelable<Parcelable>(key) as T
    }