package com.shishkin.itransition.gui.nba.mappers

import com.shishkin.itransition.db.NbaPlayerLocal
import com.shishkin.itransition.db.NbaTeamLocal
import com.shishkin.itransition.gui.utils.Mapper
import com.shishkin.itransition.network.entities.NbaPlayerRemote
import com.shishkin.itransition.network.entities.NbaTeamRemote
import javax.inject.Inject

class NbaPlayerRemoteToLocalMapper @Inject constructor() :
    Mapper<List<NbaPlayerRemote>, List<NbaPlayerLocal>> {

    override fun invoke(input: List<NbaPlayerRemote>): List<NbaPlayerLocal> {
        return input.map { nbaPlayerRemote ->
            NbaPlayerLocal(
                nbaPlayerId = nbaPlayerRemote.id,
                firstName = nbaPlayerRemote.firstName,
                heightFeet = nbaPlayerRemote.heightFeet,
                heightInches = nbaPlayerRemote.heightInches,
                lastName = nbaPlayerRemote.lastName,
                position = nbaPlayerRemote.position,
                teamId = mapFromRemoteToLocalNbaTeam(nbaPlayerRemote.team).id,
                weightPounds = nbaPlayerRemote.weightPounds
            )
        }
    }

    private fun mapFromRemoteToLocalNbaTeam(nbaTeamRemote: NbaTeamRemote): NbaTeamLocal {
        return NbaTeamLocal(
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