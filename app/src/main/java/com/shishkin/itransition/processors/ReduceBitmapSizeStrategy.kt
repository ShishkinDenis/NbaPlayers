package com.shishkin.itransition.processors

import android.graphics.Bitmap

private const val DEFAULT_IMAGE_RATIO = 1f
private const val MAX_IMAGE_SIZE_PX = 800

class ReduceBitmapSizeStrategy(private val maxImageSize: Int = MAX_IMAGE_SIZE_PX) :
    ProcessBitmapStrategy {

    override fun processBitmap(bitmap: Bitmap): Bitmap {

        val longestSide =
            if (bitmap.width > bitmap.height) bitmap.width.toFloat() else bitmap.height.toFloat()
        var ratio = longestSide / maxImageSize
        if (bitmap.width <= maxImageSize && bitmap.height <= maxImageSize && ratio == 0f) {
            ratio = DEFAULT_IMAGE_RATIO
        }
        return Bitmap.createScaledBitmap(
            bitmap,
            (bitmap.width / ratio).toInt(),
            (bitmap.height / ratio).toInt(),
            true
        )
    }
}