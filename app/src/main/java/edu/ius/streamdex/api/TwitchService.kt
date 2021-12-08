package edu.ius.streamdex.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TwitchService {
    @GET("streams/")
    fun getStream(@Query("user_id")user_ids: List<Int>): Call<StreamResponse>

    @GET("search/channels/")
    fun searchUser(@Query("query")userName: String?): Call<StreamerResponse>

    @GET("users/")
    fun getUsersById(@Query("id")user_ids: List<Int>): Call<StreamerResponse>

    @GET("search/channels/")
    fun searchUsersById(@Query("query")user_ids: List<Int>): Call<StreamerResponse>
}