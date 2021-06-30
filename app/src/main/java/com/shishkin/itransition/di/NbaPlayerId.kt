package com.shishkin.itransition.di

import javax.inject.Qualifier

// TODO Evgeny специальные даггер классы, которые относятся к одной специальной фиче, лучше располагать
// в фича-пакете. Т.е. этот NbaPlayerId расположить в gui -> nbadetails

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class NbaPlayerId