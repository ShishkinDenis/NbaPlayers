package com.shishkin.itransition.network.entities

class NbaPlayersMapper : Mapper<List<NbaPlayer>, List<ListItem>> {
    private val VIEW_TYPE_NBA_PLAYER = 1
    private val VIEW_TYPE_NBA_TEAM = 2

    override fun invoke(input: List<NbaPlayer>): List<ListItem> {
        val listOfListItem = ArrayList<ListItem>()
        for (item in input) {
            val nbaPlayerListItem = ListItem(item, VIEW_TYPE_NBA_PLAYER)
            val nbaTeamListItem = ListItem(item.team, VIEW_TYPE_NBA_TEAM)
            listOfListItem.add(nbaPlayerListItem)
            listOfListItem.add(nbaTeamListItem)
        }
        return listOfListItem
    }
}