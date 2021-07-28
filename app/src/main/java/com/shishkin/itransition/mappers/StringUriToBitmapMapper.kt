package com.shishkin.itransition.mappers

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import javax.inject.Inject

class StringUriToBitmapMapper @Inject constructor() {

    fun convertUriToBitmap(
        contentResolver: ContentResolver,
        inputImageStringUri: String
    ): Bitmap {
        return BitmapFactory.decodeStream(
            contentResolver.openInputStream(
                Uri.parse(
                    inputImageStringUri
                )
            )
        )
    }
}