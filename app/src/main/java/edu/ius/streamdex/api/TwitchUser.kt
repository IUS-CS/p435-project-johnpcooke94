package edu.ius.streamdex.api

data class TwitchUser(
    val id: Int,
    val display_name: String,
    val is_live: Boolean,
    val title: String
)