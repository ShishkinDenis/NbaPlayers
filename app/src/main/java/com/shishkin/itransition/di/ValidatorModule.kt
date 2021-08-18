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
    @ProfileUserNameValidator
    fun provideUserNameValidator(): Validator<String> {
        val rules: Set<Rule<String>> =
            setOf(TextMinLengthRule(4), TextWithoutDigitsRule())
        return Validator<String>().apply { addRules(rules) }
    }

    @Provides
    @LoginUserNameValidator
    fun provideLoginUserNameValidator(): Validator<String> {
        val rules: Set<Rule<String>> = setOf(TextMinLengthRule(1))
        return Validator<String>().apply { addRules(rules) }
    }

    @Provides
    @ProfileBirthDateValidator
    fun provideBirthDateValidator(): Validator<Date?> {
        val rules: Set<Rule<Date?>> =
            setOf(ChosenDateBeforeCurrentDateRule(), DateIsNotNullRule())
        return Validator<Date?>().apply { addRules(rules) }
    }

    @Provides
    @ProfileImageUriValidator
    fun provideImageUriValidator(): Validator<Uri?> {
        val rules: Set<Rule<Uri?>> = setOf(UriIsNotNullRule())
        return Validator<Uri?>().apply { addRules(rules) }
    }

    @Provides
    @LoginUserPasswordValidator
    fun provideUserPasswordValidator(): Validator<String> {
        val rules: Set<Rule<String>> = setOf(TextMinLengthRule(6))
        return Validator<String>().apply { addRules(rules) }
    }
}