package edu.ius.streamdex.api

data class TwitchStream(
    val id: String,
    val user_id: String,
    val user_name: String,
    val type: String,
    val title: String,
    val thumbnail_url: String
)
