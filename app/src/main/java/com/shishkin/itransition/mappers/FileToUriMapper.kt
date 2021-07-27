package com.shishkin.itransition.mappers

import android.net.Uri
import java.io.File

class FileToUriMapper {

    fun convertFileToUri(file: File): Uri {
        return Uri.fromFile(file)
    }
}