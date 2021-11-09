package com.keelim.core.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat.JPEG
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.PrecomputedTextCompat
import androidx.core.view.drawToBitmap
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.widget.TextViewCompat
import coil.imageLoader
import coil.load
import coil.request.ImageRequest
import java.io.File
import java.io.FileOutputStream

fun ImageView.loadAsync(url: String?, @DrawableRes placeholder: Int? = null) {
    if (url == null) {
        placeholder?.let { load(it) }
    } else {
        load(url) {
            if (placeholder != null) {
                placeholder(placeholder)
            }
            crossfade(true)
        }
    }
}

fun ImageView.loadAsync(url: String?, doOnEnd: () -> Unit) {
    load(url) {
        listener(
            onSuccess = { _, _ -> doOnEnd() },
            onError = { _, _ -> doOnEnd() }
        )
    }
}

fun ImageView.setGrayscale(enable: Boolean) {
    colorFilter = if (enable) {
        ColorMatrixColorFilter(ColorMatrix().apply { setSaturation(0f) })
    } else {
        null
    }
}

suspend fun Context.loadAsync(url: String): Bitmap? {
    val request = ImageRequest.Builder(this)
        .data(url)
        .build()
    val result = imageLoader.execute(request).drawable
    return (result as? BitmapDrawable)?.bitmap
}

fun TextView.asyncText(text: CharSequence?) {
    if (text == null) return
    if (this is AppCompatTextView) {
        val params = TextViewCompat.getTextMetricsParams(this)
        this.setTextFuture(PrecomputedTextCompat.getTextFuture(text, params, null))
    }
}

fun View.animateVisible(
    isVisible: Boolean,
    startDelay: Long = 0,
    duration: Long = 30
) {
    animate()
        .setStartDelay(startDelay)
        .setDuration(duration)
        .alpha(if (isVisible) 1f else 0f)
        .withEndAction { this.isVisible = isVisible }
}

fun View.animateInvisible(
    isInvisible: Boolean,
    startDelay: Long = 0,
    duration: Long = 30
) {
    animate()
        .setStartDelay(startDelay)
        .setDuration(duration)
        .alpha(if (isInvisible) 0f else 1f)
        .withEndAction { this.isInvisible = isInvisible }
}

fun View.animateGone(
    isGone: Boolean,
    startDelay: Long = 0,
    duration: Long = 300
) {
    animate()
        .setStartDelay(startDelay)
        .setDuration(duration)
        .alpha(if (isGone) 0f else 1f)
        .withEndAction { this.isGone = isGone }
}

fun View.toCacheFile(folderName: String? = null, fileName: String): File {
    return drawToBitmap().toCacheFile(context, folderName = folderName, fileName = fileName)
}

fun Bitmap.toCacheFile(
    context: Context,
    folderName: String? = null,
    fileName: String
): File {
    val cacheDir = if (folderName.isNullOrBlank()) {
        context.cacheDir
    } else {
        File(context.cacheDir, folderName).apply { mkdirs() }
    }
    return File(cacheDir, fileName).apply {
        FileOutputStream(this).use {
            compress(JPEG, 100, it)
            it.flush()
        }
    }
}