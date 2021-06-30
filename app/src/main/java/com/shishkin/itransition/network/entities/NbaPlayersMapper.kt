package com.shishkin.itransition.network.entities

// TODO Evgeny: Почему в  пакете network entities маппер лежит, и почему он использует еще одни View Type?
// Получается, View Type у тебя обьявлен в 3-х местах, что неправильно совершенно

class NbaPlayersMapper : Mapper<List<NbaPlayer>, List<ListItem>> {
    private val VIEW_TYPE_NBA_PLAYER = 1
    private val VIEW_TYPE_NBA_TEAM = 2

    override fun invoke(input: List<NbaPlayer>): List<ListItem> {
        val listOfListItem = ArrayList<ListItem>()
        // Вместо in лучше использовать input.forEach {}
        for (item in input) {
            val nbaPlayerListItem = ListItem(item, VIEW_TYPE_NBA_PLAYER)
            val nbaTeamListItem = ListItem(item.team, VIEW_TYPE_NBA_TEAM)
            listOfListItem.add(nbaPlayerListItem)
            listOfListItem.add(nbaTeamListItem)
        }
        return listOfListItem
    }
}