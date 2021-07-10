package com.shishkin.itransition.gui.nba.mappers

import com.shishkin.itransition.gui.nba.VIEW_TYPE_NBA_PLAYER
import com.shishkin.itransition.gui.nba.VIEW_TYPE_NBA_TEAM
import com.shishkin.itransition.gui.nba.uientities.NbaPlayerUi
import com.shishkin.itransition.gui.utils.ListItem
import com.shishkin.itransition.gui.utils.Mapper
import javax.inject.Inject

class NbaPlayerUiToListItemMapper @Inject constructor() :
    Mapper<List<NbaPlayerUi>, List<ListItem>> {

    override fun invoke(input: List<NbaPlayerUi>): List<ListItem> {
        val listOfListItem = ArrayList<ListItem>()
        input.forEach { nbaPlayer ->
            val nbaPlayerListItem = ListItem(nbaPlayer, VIEW_TYPE_NBA_PLAYER)
            val nbaTeamListItem = ListItem(nbaPlayer.team, VIEW_TYPE_NBA_TEAM)
            listOfListItem.add(nbaPlayerListItem)
            listOfListItem.add(nbaTeamListItem)
        }
        return listOfListItem
    }
}