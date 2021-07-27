package com.shishkin.itransition.processors

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import java.io.File
import java.io.FileOutputStream

class FileProcessor {

    fun saveBitmapToFile(
        context: Context,
        bitmap: Bitmap,
        fileName: String,
        imageQuality: Int
    ): File {
        val outputFile = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName)
        outputFile.createNewFile()
        val out = FileOutputStream(outputFile)
        bitmap.compress(Bitmap.CompressFormat.PNG, imageQuality, out)
        return outputFile
    }
}