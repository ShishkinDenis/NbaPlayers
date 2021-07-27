package com.shishkin.itransition.processors

import android.graphics.Bitmap

interface ProcessBitmapStrategy {

    fun processBitmap(bitmap: Bitmap): Bitmap
}