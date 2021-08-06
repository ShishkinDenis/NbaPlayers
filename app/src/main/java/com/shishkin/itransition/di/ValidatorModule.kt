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
        val rules: List<Rule<String>> =
            listOf(TextMinLengthRule(4), TextWithoutDigitsRule())
        return Validator<String>().addRules(rules)
    }

    @Provides
    @BirthDateValidator
    fun provideBirthDateValidator(): Validator<Date?> {
        val rules: List<Rule<Date?>> = listOf(
            ChosenDateBeforeCurrentDateRule(),
            DateIsNotEmptyRule()
        )
        return Validator<Date?>().addRules(rules)
    }

    @Provides
    @ImageUriValidator
    fun provideImageUriValidator(): Validator<Uri?> {
        val rules: List<Rule<Uri?>> = listOf(
            UriIsNotNullRule()
        )
        return Validator<Uri?>().addRules(rules)
    }
}