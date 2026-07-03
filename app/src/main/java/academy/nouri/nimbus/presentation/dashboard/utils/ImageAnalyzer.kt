package academy.nouri.nimbus.presentation.dashboard.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import androidx.core.net.toUri
import androidx.palette.graphics.Palette
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun analyzeImageBrightness(context: Context, imagePath: String): Boolean {
    return withContext(Dispatchers.IO) {
        try {
            val uri = imagePath.toUri()
            val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                // API 28+ : ImageDecoder
                val source = ImageDecoder.createSource(context.contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
            } else {
                // API < 28 : BitmapFactory + InputStream
                context.contentResolver.openInputStream(uri)?.use { stream ->
                    BitmapFactory.decodeStream(stream)
                } ?: throw Exception("Cannot open InputStream")
            }

            val softwareBitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
                && bitmap.config == Bitmap.Config.HARDWARE
            ) {
                bitmap.copy(Bitmap.Config.ARGB_8888, false)
            } else {
                bitmap
            }

            val palette = Palette.from(softwareBitmap).generate()
            val luminance = palette.dominantSwatch?.hsl?.get(2) ?: 0f

            luminance < 0.5f // true = dark, false = light
        } catch (_: Exception) {
            true
        }
    }
}