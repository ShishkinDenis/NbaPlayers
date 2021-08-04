package com.shishkin.itransition.gui.edituserprofile

import android.net.Uri

data class UserUi(
    val id: Int,
    val name: String,
    val birthDate: String,
    val profileImageUri: Uri?
)
