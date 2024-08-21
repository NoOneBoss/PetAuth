package me.nooneboss.petauthgui.stuff

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import io.github.g0dkar.qrcode.QRCode
import org.jetbrains.skia.Image

object Extensions {
    fun ByteArray.toImageBitmap(): ImageBitmap = Image.makeFromEncoded(this).toComposeImageBitmap()
    fun generateQRCode(url: String, background: Int, foreground: Int): ImageBitmap {
        return QRCode(url).render(
            brightColor = background,
            darkColor = foreground
        ).getBytes().toImageBitmap()
    }

    fun getGreeting(hour: Int): String {
        return when (hour) {
            in 6..11 -> "ое утро ☕"
            in 12..17 -> "ый день \uD83C\uDF07"
            in 18..22 -> "ый вечер \uD83C\uDF06"
            else -> "ой ночи \uD83C\uDF03"
        }
    }
}