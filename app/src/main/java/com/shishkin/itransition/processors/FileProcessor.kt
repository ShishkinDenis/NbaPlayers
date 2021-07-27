package com.shishkin.itransition.processors

import android.content.Context
import android.graphics.Bitmap
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

        val outputDir = File(context.filesDir, filePath)
        val outputFile = File(outputDir, fileName)
        val out = FileOutputStream(outputFile)
        //    TODO add choice of formats
        bitmap.compress(Bitmap.CompressFormat.PNG, imageQuality, out)
        return outputFile
    }
}