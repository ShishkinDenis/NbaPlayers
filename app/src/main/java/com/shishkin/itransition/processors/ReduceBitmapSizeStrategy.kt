package com.shishkin.itransition.processors

import android.graphics.Bitmap
import javax.inject.Inject

private const val DEFAULT_IMAGE_RATIO = 1f
private const val MAX_IMAGE_SIZE_PX = 800

class ReduceBitmapSizeStrategy @Inject constructor() :
    ProcessBitmapStrategy {

    var maxImageSize: Int = MAX_IMAGE_SIZE_PX
        set(value) {
            field = if (value <= MAX_IMAGE_SIZE_PX)
                value
            else {
                MAX_IMAGE_SIZE_PX
            }
        }

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