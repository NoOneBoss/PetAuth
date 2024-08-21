package me.nooneboss.petauthgui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import me.nooneboss.petauthgui.config.Config.loadConfig
import me.nooneboss.petauthgui.config.Config.saveConfig
import me.nooneboss.petauthgui.windows.QrWindow
import java.util.*

@Composable
@Preview
fun App() {
    val config = loadConfig("src/main/resources/config.yml")

    if(config.auth.uuid.isEmpty()) {
        config.auth.uuid = UUID.randomUUID().toString()
        saveConfig("src/main/resources/config.yml", config)
    }

    MaterialTheme {
        QrWindow(config.server.url, config.auth.uuid).start()
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "PetAuth") {
        App()
    }
}
