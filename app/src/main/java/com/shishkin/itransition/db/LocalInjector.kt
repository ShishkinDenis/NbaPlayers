package com.shishkin.itransition.db

object LocalInjector {
    var nbaPlayerDataBase : NbaPlayerDataBase? = null

    fun injectDb(): NbaPlayerDataBase? {
        return nbaPlayerDataBase
    }
}