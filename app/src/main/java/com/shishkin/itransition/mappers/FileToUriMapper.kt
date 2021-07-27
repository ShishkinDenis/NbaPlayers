package com.shishkin.itransition.mappers

import android.net.Uri
import java.io.File
import javax.inject.Inject

class FileToUriMapper @Inject constructor() {

    fun convertFileToUri(file: File): Uri {
        return Uri.fromFile(file)
    }
}