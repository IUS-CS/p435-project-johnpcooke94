package edu.ius.streamdex.api

data class TwitchUser(
    val id: String,
    val login: String,
    val display_name: String,
    val type: String
)