package edu.ius.streamdex.api

data class TwitchUser(
    val display_name: String,
    val is_live: Boolean,
    val title: String
)