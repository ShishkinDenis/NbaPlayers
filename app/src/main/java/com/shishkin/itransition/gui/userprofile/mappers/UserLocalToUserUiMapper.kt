package com.shishkin.itransition.gui.userprofile.mappers

import android.net.Uri
import com.shishkin.itransition.db.UserLocal
import com.shishkin.itransition.gui.edituserprofile.UserUi
import com.shishkin.itransition.gui.utils.Mapper
import javax.inject.Inject

class UserLocalToUserUiMapper @Inject constructor() : Mapper<UserLocal, UserUi> {

    override fun invoke(input: UserLocal): UserUi {
        return UserUi(
            id = input.id,
            name = input.name,
            birthDate = input.birthDate,
            profileImageUri = null // TODO: Add wrapper to parse or event use URI string here?
        )
    }
}


