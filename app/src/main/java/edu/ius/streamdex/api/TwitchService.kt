package edu.ius.streamdex.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TwitchService {
    @GET("streams/")
    fun getStream(@Query("user_login")userLogin: String?): Call<StreamResponse>

    @GET("search/channels/")
    fun getUser(@Query("query")userName: String?): Call<StreamerResponse>
}