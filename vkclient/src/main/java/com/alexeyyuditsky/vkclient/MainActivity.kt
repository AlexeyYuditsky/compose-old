package com.alexeyyuditsky.vkclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.alexeyyuditsky.vkclient.core.log
import com.alexeyyuditsky.vkclient.ui.ActivityResultTest
import com.alexeyyuditsky.vkclient.ui.MainScreen
import com.alexeyyuditsky.vkclient.ui.theme.VkClientTheme
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VkClientTheme {
                val someValue = remember { mutableStateOf(true) }

                log("Recomposition ${someValue.value}")
                val launcher = rememberLauncherForActivityResult(
                    contract = VK.getVKAuthActivityResultContract()
                ) {
                    when (it) {
                        is VKAuthenticationResult.Success -> {
                            log("Success")
                            // User passed authorization
                        }

                        is VKAuthenticationResult.Failed -> {
                            log("Failed")
                            // User didn't pass authorization
                        }
                    }
                }
                LaunchedEffect(key1 = Unit) {
                    log("LaunchedEffect")
                }
                SideEffect {
                    log("SideEffect")
                    //launcher.launch(listOf(VKScope.WALL))
                }
                //MainScreen()
                Button(onClick = { someValue.value = !someValue.value }) {
                    Text(text = "Change state")
                }
            }
        }
    }
}