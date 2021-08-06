package com.shishkin.itransition.validators.rules

import android.net.Uri

class UriIsNotNullRule : Rule<Uri?> {

    override fun isValid(obj: Uri?): Boolean {
        return obj != null
    }
}