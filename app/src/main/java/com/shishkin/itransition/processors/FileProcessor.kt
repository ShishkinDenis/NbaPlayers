package com.shishkin.itransition.processors

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class FileProcessor @Inject constructor() {

    fun saveBitmapToFile(
        context: Context,
        bitmap: Bitmap,
        fileName: String,
        filePath: String,
        imageQuality: Int
    ): File {
        val outputFile = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName)
        outputFile.createNewFile()
        val out = FileOutputStream(outputFile)
        bitmap.compress(Bitmap.CompressFormat.PNG, imageQuality, out)
        return outputFile
    }
}