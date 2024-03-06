package com.alexeyyuditsky.vkclient.ui

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alexeyyuditsky.vkclient.core.log

@Composable
fun SideEffectTest(number: MyNumber) {
    Handler(Looper.getMainLooper()).post{
        number.a = 2
        log("change")
    }

    Column {
        LazyColumn {
            items(500) {
                log("item")
                Text(text = number.a.toString())
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn {
            items(500) {
                log("item")
                Text(text = number.a.toString())
            }
        }
    }
}

data class MyNumber(var a: Int)