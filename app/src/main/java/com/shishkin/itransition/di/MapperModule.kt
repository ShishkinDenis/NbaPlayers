package com.shishkin.itransition.di

import com.shishkin.itransition.db.UserLocal
import com.shishkin.itransition.gui.edituserprofile.UserUi
import com.shishkin.itransition.gui.userprofile.mappers.UserLocalToUserUiMapper
import com.shishkin.itransition.gui.utils.Mapper
import dagger.Module
import dagger.Provides

@Module
class MapperModule {

    @Provides
    fun provideUserLocalToUserUiMapper(): Mapper<UserLocal, UserUi> {
        return UserLocalToUserUiMapper()
    }
}