package me.nooneboss.petauthgui.windows

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.g0dkar.qrcode.render.Colors
import me.nooneboss.petauthgui.stuff.Extensions
import java.util.*

class QrWindow(private val address: String, private val uuid: String) {
    private val background = Colors.css("#ffffff")
    private val foreground = Colors.css("#000000")

    @Composable
    fun start() {
        val qrCodeUrl by remember { mutableStateOf("$address/$uuid") }
        val image by remember { mutableStateOf(Extensions.generateQRCode(qrCodeUrl, background, foreground)) }

        val isLogin by remember { mutableStateOf(false) }

        val greeting = Extensions.getGreeting(Calendar.getInstance().get(Calendar.HOUR_OF_DAY))
        MaterialTheme {
            if(isLogin) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "Добр$greeting",
                        style = TextStyle(fontSize = 30.sp)
                    )
                    Text(
                        text = "Вы успешно отметили своё присутствие!",
                        style = TextStyle(fontSize = 15.sp)
                    )
                }
            }
            else {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Добр$greeting",
                            style = TextStyle(fontSize = 30.sp)
                        )
                        Spacer(modifier = Modifier.size(10.dp))
                        Image(
                            bitmap = image,
                            contentDescription = "image",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.size(250.dp, 250.dp)
                        )
                        Spacer(modifier = Modifier.size(10.dp))
                        Text(
                            text = "Ваш ID: $uuid",
                            style = TextStyle(fontSize = 13.sp, color = Color.LightGray)
                        )
                    }
                }
            }
        }
    }
}