package com.shishkin.itransition.di

import com.shishkin.itransition.gui.nbadetails.NbaDetailsFragment
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.InternalCoroutinesApi

// TODO Evgeny специальные даггер классы, которые относятся к одной специальной фиче, лучше располагать
// в фича-пакете: Тоже самое. Расположить в фича-пакете

@Module
class NbaPlayerIdModule {
    @InternalCoroutinesApi
    @Provides
    @NbaPlayerId
    fun provideNbaPlayerId(nbaDetailsFragment: NbaDetailsFragment): Int? {
        // TODO Evgeny: магиеческая константа id. Так не стоит делать.
        // Из хороших решений, расположить этот аргумент в специальном strings-файлике, аля создать
        // string-args.xml и там создать этот ресурс, а тут доставать через nbaDetailsFragment.getString()
        return nbaDetailsFragment.arguments?.getInt("id")
    }
}