package com.shishkin.itransition.gui.nba

import com.shishkin.itransition.gui.utils.ListItem
import com.shishkin.itransition.gui.utils.Mapper
import com.shishkin.itransition.network.entities.NbaPlayer

class NbaPlayersMapper : Mapper<List<NbaPlayer>, List<ListItem>> {

    override fun invoke(input: List<NbaPlayer>): List<ListItem> {
        val listOfListItem = ArrayList<ListItem>()
         input.forEach {
            val nbaPlayerListItem = ListItem(it, VIEW_TYPE_NBA_PLAYER)
            val nbaTeamListItem = ListItem(it.team, VIEW_TYPE_NBA_TEAM)
            listOfListItem.add(nbaPlayerListItem)
            listOfListItem.add(nbaTeamListItem)
        }
        return listOfListItem
    }
}