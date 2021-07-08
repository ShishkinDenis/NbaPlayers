package com.shishkin.itransition.db

import com.shishkin.itransition.network.entities.NbaPlayerRemote
import com.shishkin.itransition.network.entities.NbaTeamRemote

fun NbaPlayerRemote.toNbaPlayerLocal() = NbaPlayerLocal(
    id = this.id,
    firstName = this.firstName,
    heightFeet = this.heightFeet,
    heightInches = this.heightInches,
    lastName = this.lastName,
    position = this.position,
    team = this.team.toNbaTeamLocal(),
    weightPounds = this.weightPounds
)

fun NbaPlayerLocal.toNbaPlayerRemote() = NbaPlayerRemote(
    id = this.id,
    firstName = this.firstName,
    heightFeet = this.heightFeet,
    heightInches = this.heightInches,
    lastName = this.lastName,
    position = this.position,
    team = this.team.toNbaTeamRemote(),
    weightPounds = this.weightPounds
)

fun List<NbaPlayerRemote>.toNbaPlayerLocalList() = this.map { it.toNbaPlayerLocal() }

fun List<NbaPlayerLocal>.toNbaPlayerRemoteList() = this.map { it.toNbaPlayerRemote() }

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