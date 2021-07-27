package com.shishkin.itransition.mappers

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri

class StringUriToBitmapMapper {

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