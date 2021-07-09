package com.shishkin.itransition.gui.nba.mappers

import com.shishkin.itransition.db.NbaTeamLocal
import com.shishkin.itransition.db.PlayerWithTeam
import com.shishkin.itransition.gui.utils.Mapper
import com.shishkin.itransition.network.entities.NbaPlayerRemote
import com.shishkin.itransition.network.entities.NbaTeamRemote

class PlayerWithTeamToNbaPlayerRemoteMapper : Mapper<List<PlayerWithTeam>, List<NbaPlayerRemote>> {

    override fun invoke(input: List<PlayerWithTeam>): List<NbaPlayerRemote> {
        return input.map { playerWithTeam ->
            mapFromPlayerWithTeamToRemoteNbaPlayer(playerWithTeam)
        }
    }

    private fun mapFromLocalToRemoteNbaTeam(nbaTeamLocal: NbaTeamLocal): NbaTeamRemote {
        return NbaTeamRemote(
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

    fun mapFromPlayerWithTeamToRemoteNbaPlayer(playerWithTeam: PlayerWithTeam): NbaPlayerRemote {
        return NbaPlayerRemote(
            id = playerWithTeam.player.nbaPlayerId,
            firstName = playerWithTeam.player.firstName,
            heightFeet = playerWithTeam.player.heightFeet,
            heightInches = playerWithTeam.player.heightInches,
            lastName = playerWithTeam.player.lastName,
            position = playerWithTeam.player.position,
            team = mapFromLocalToRemoteNbaTeam(playerWithTeam.team),
            weightPounds = playerWithTeam.player.weightPounds
        )
    }
}