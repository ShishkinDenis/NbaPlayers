package com.shishkin.itransition.gui.nba.mappers

import com.shishkin.itransition.gui.nba.uientities.NbaPlayerUi
import com.shishkin.itransition.gui.nba.uientities.NbaTeamUi
import com.shishkin.itransition.gui.utils.Mapper
import com.shishkin.itransition.network.entities.NbaPlayerRemote
import com.shishkin.itransition.network.entities.NbaTeamRemote

class NbaPlayerRemoteToUiMapper : Mapper<List<NbaPlayerRemote>, List<NbaPlayerUi>> {

    override fun invoke(input: List<NbaPlayerRemote>): List<NbaPlayerUi> {
        return input.map { nbaPlayerRemote ->
            NbaPlayerUi(
                id = nbaPlayerRemote.id,
                firstName = nbaPlayerRemote.firstName,
                heightFeet = nbaPlayerRemote.heightFeet,
                heightInches = nbaPlayerRemote.heightInches,
                lastName = nbaPlayerRemote.lastName,
                position = nbaPlayerRemote.position,
                team = mapFromRemoteToUiNbaTeam(nbaPlayerRemote.team),
                weightPounds = nbaPlayerRemote.weightPounds
            )
        }
    }

    private fun mapFromRemoteToUiNbaTeam(nbaTeamRemote: NbaTeamRemote): NbaTeamUi {
        return NbaTeamUi(
            id = nbaTeamRemote.id,
            abbreviation = nbaTeamRemote.abbreviation,
            city = nbaTeamRemote.city,
            conference = nbaTeamRemote.conference,
            division = nbaTeamRemote.division,
            fullName = nbaTeamRemote.fullName,
            name = nbaTeamRemote.name,
            homeTeamScore = nbaTeamRemote.homeTeamScore,
            visitorTeamScore = nbaTeamRemote.visitorTeamScore
        )
    }
}