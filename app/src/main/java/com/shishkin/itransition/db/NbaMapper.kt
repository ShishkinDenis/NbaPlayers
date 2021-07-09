package com.shishkin.itransition.db

import com.shishkin.itransition.network.entities.NbaPlayerRemote
import com.shishkin.itransition.network.entities.NbaTeamRemote

fun NbaPlayerRemote.toNbaTeamLocal() = NbaTeamLocal(
    id = this.team.id,
    abbreviation = this.team.abbreviation,
    city = this.team.city,
    conference = this.team.conference,
    division = this.team.division,
    fullName = this.team.fullName,
    name = this.team.name,
    homeTeamScore = this.team.homeTeamScore,
    visitorTeamScore = this.team.visitorTeamScore
)

fun List<NbaPlayerRemote>.toNbaTeamLocalList() = this.map { it.toNbaTeamLocal() }

fun PlayerWithTeam.toNbaPlayerRemote() = NbaPlayerRemote(
    id = this.player.nbaPlayerId,
    firstName = this.player.firstName,
    heightFeet = this.player.heightFeet,
    heightInches = this.player.heightInches,
    lastName = this.player.lastName,
    position = this.player.position,
    team = this.team.toNbaTeamRemote(),
    weightPounds = this.player.weightPounds
)

fun List<PlayerWithTeam>.toNbaPlayerRemoteList() = this.map { it.toNbaPlayerRemote() }

fun NbaPlayerRemote.toNbaPlayerLocal() = NbaPlayerLocal(
    nbaPlayerId = this.id,
    firstName = this.firstName,
    heightFeet = this.heightFeet,
    heightInches = this.heightInches,
    lastName = this.lastName,
    position = this.position,
    teamId = this.team.toNbaTeamLocal().id,
    weightPounds = this.weightPounds
)

fun List<NbaPlayerRemote>.toNbaPlayerLocalList() = this.map { it.toNbaPlayerLocal() }

fun NbaTeamRemote.toNbaTeamLocal() = NbaTeamLocal(
    id = this.id,
    abbreviation = this.abbreviation,
    city = this.city,
    conference = this.conference,
    division = this.division,
    fullName = this.fullName,
    name = this.name,
    homeTeamScore = this.homeTeamScore,
    visitorTeamScore = this.visitorTeamScore
)

fun NbaTeamLocal.toNbaTeamRemote() = NbaTeamRemote(
    id = this.id,
    abbreviation = this.abbreviation,
    city = this.city,
    conference = this.conference,
    division = this.division,
    fullName = this.fullName,
    name = this.name,
    homeTeamScore = this.homeTeamScore,
    visitorTeamScore = this.visitorTeamScore
)
