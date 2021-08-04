package com.shishkin.itransition.gui.userprofile.mappers

import com.shishkin.itransition.db.UserLocal
import com.shishkin.itransition.gui.edituserprofile.UserUi
import com.shishkin.itransition.gui.utils.Mapper
import javax.inject.Inject

class UserUiToUserLocalMapper @Inject constructor() : Mapper<UserUi, UserLocal> {

    override fun invoke(input: UserUi): UserLocal {
        return UserLocal(
            id = input.id,
            name = input.name,
            birthDate = input.birthDate,
            profileImageUri = input.profileImageUri?.toString() ?: ""
        )
    }
}