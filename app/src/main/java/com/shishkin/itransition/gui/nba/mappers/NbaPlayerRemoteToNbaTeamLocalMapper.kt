package com.shishkin.itransition.gui.nba.mappers

import com.shishkin.itransition.db.NbaTeamLocal
import com.shishkin.itransition.gui.utils.Mapper
import com.shishkin.itransition.network.entities.NbaPlayerRemote
import javax.inject.Inject

class NbaPlayerRemoteToNbaTeamLocalMapper @Inject constructor() :
    Mapper<List<NbaPlayerRemote>, List<NbaTeamLocal>> {

    override fun invoke(input: List<NbaPlayerRemote>): List<NbaTeamLocal> {
        return input.map { nbaPlayerRemote ->
            NbaTeamLocal(
                id = nbaPlayerRemote.team.id,
                abbreviation = nbaPlayerRemote.team.abbreviation,
                city = nbaPlayerRemote.team.city,
                conference = nbaPlayerRemote.team.conference,
                division = nbaPlayerRemote.team.division,
                fullName = nbaPlayerRemote.team.fullName,
                name = nbaPlayerRemote.team.name,
                homeTeamScore = nbaPlayerRemote.team.homeTeamScore,
                visitorTeamScore = nbaPlayerRemote.team.visitorTeamScore
            )
        }
    }
}