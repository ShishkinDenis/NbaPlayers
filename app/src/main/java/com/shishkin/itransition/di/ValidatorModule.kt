package com.shishkin.itransition.di

import android.net.Uri
import com.shishkin.itransition.validators.Validator
import com.shishkin.itransition.validators.rules.*
import dagger.Module
import dagger.Provides
import java.util.*

@Module
class ValidatorModule {

    @Provides
    @UserNameValidator
    fun provideUserNameValidator(): Validator<String> {
        val rules: Set<Rule<String>> =
            setOf(TextMinLengthRule(4), TextWithoutDigitsRule())
        return Validator<String>().apply { addRules(rules) }
    }

    @Provides
    @BirthDateValidator
    fun provideBirthDateValidator(): Validator<Date?> {
        val rules: Set<Rule<Date?>> =
            setOf(ChosenDateBeforeCurrentDateRule(), DateIsNotNullRule())
        return Validator<Date?>().apply { addRules(rules) }
    }

    @Provides
    @ImageUriValidator
    fun provideImageUriValidator(): Validator<Uri?> {
        val rules: Set<Rule<Uri?>> = setOf(UriIsNotNullRule())
        return Validator<Uri?>().apply { addRules(rules) }
    }
}