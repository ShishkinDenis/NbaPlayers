package com.shishkin.itransition.gui.games

import com.shishkin.itransition.network.entities.NbaGame

interface NbaGameItemListener {

    fun onClickedNbaGame(nbaGame: NbaGame?)
}