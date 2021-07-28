package com.shishkin.itransition.processors

import android.graphics.Bitmap
import javax.inject.Inject

class BitmapProcessor @Inject constructor() {

    fun processBitmap(bitmap: Bitmap?, vararg strategies: ProcessBitmapStrategy): Bitmap? {
        if (bitmap == null) return null

        var processedBitmap = bitmap
        for (strategy in strategies) {
            processedBitmap = strategy.processBitmap(bitmap)
        }
        return processedBitmap
    }
}