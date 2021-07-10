package com.shishkin.itransition.gui.nba.mappers

import com.shishkin.itransition.db.NbaTeamLocal
import com.shishkin.itransition.db.PlayerWithTeam
import com.shishkin.itransition.gui.nba.uientities.NbaPlayerUi
import com.shishkin.itransition.gui.nba.uientities.NbaTeamUi
import com.shishkin.itransition.gui.utils.Mapper
import javax.inject.Inject

class PlayerWithTeamToNbaPlayerUiMapper @Inject constructor() :
    Mapper<List<PlayerWithTeam>, List<NbaPlayerUi>> {

    override fun invoke(input: List<PlayerWithTeam>): List<NbaPlayerUi> {
        return input.map { playerWithTeam ->
            mapFromPlayerWithTeamToNbaPlayerUi(playerWithTeam)
        }
    }

    private fun mapFromLocalToNbaTeamUi(nbaTeamLocal: NbaTeamLocal): NbaTeamUi {
        return NbaTeamUi(
            id = nbaTeamLocal.id,
            abbreviation = nbaTeamLocal.abbreviation,
            city = nbaTeamLocal.city,
            conference = nbaTeamLocal.conference,
            division = nbaTeamLocal.division,
            fullName = nbaTeamLocal.fullName,
            name = nbaTeamLocal.name,
            homeTeamScore = nbaTeamLocal.homeTeamScore,
            visitorTeamScore = nbaTeamLocal.visitorTeamScore
        )
    }

    fun mapFromPlayerWithTeamToNbaPlayerUi(playerWithTeam: PlayerWithTeam): NbaPlayerUi {
        return NbaPlayerUi(
            id = playerWithTeam.player.nbaPlayerId,
            firstName = playerWithTeam.player.firstName,
            heightFeet = playerWithTeam.player.heightFeet,
            heightInches = playerWithTeam.player.heightInches,
            lastName = playerWithTeam.player.lastName,
            position = playerWithTeam.player.position,
            team = mapFromLocalToNbaTeamUi(playerWithTeam.team),
            weightPounds = playerWithTeam.player.weightPounds
        )
    }
}