package edu.ius.streamdex.api

import android.util.Log
import androidx.lifecycle.MutableLiveData
import edu.ius.streamdex.BuildConfig
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.converter.gson.GsonConverterFactory

const val TAG = "STREAM_REPO"

class TwitchRepository {

    private class AuthInterceptor(clientId: String, authToken: String): Interceptor {

        private val headers = Headers.Builder()

        init {
            headers.add("Authorization", authToken)
            headers.add("Client-Id", clientId)
        }

        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val authenticatedRequest = request.newBuilder().headers(headers.build()).build()
            return chain.proceed(authenticatedRequest)
        }

    }

    companion object {
        private fun getClient(): OkHttpClient {
            val loggingInterceptor = HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.HEADERS)

            // CLIENT ID AND AUTH TOKEN MUST GO HERE
            val authInterceptor = AuthInterceptor(
                BuildConfig.TWITCH_CLIENT_ID,
                "Bearer ${BuildConfig.TWITCH_AUTH_TOKEN}"
                )

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(authInterceptor)
                .build()

            return client
        }

        private val retrofit = retrofit2.Retrofit.Builder()
            .baseUrl("https://api.twitch.tv/helix/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(getClient())
            .build()

        private val api = retrofit.create(TwitchService::class.java)

        fun getStreams(): MutableLiveData<StreamResponse> {
            val ret = MutableLiveData<StreamResponse>()

            api.getStream(null).enqueue(object: Callback<StreamResponse> {

                override fun onResponse(
                    call: Call<StreamResponse>,
                    response: retrofit2.Response<StreamResponse>
                ) {
                    if (!response.isSuccessful) {
                        Log.e(TAG, "Twitch API response error ${response.code()}: ${response.errorBody()}")
                        ret.postValue(null)
                    }
                    val streamList = response.body()
                    ret.postValue(streamList)
                }

                override fun onFailure(call: Call<StreamResponse>, t: Throwable) {
                    Log.e(TAG, "Unable to reach Twitch API")
                    Log.e(TAG, t.localizedMessage)
                }

            })

            return ret
        }

        fun getStreamers(username: String): MutableLiveData<StreamerResponse> {
            val ret = MutableLiveData<StreamerResponse>()

            api.getUser(username).enqueue(object: Callback<StreamerResponse> {

                override fun onResponse(
                    call: Call<StreamerResponse>,
                    response: retrofit2.Response<StreamerResponse>
                ) {
                    if (!response.isSuccessful) {
                        Log.e(TAG, "Twitch API response error ${response.code()}: ${response.errorBody()}")
                        ret.postValue(null)
                    }
                    val streamerList = response.body()
                    ret.postValue(streamerList)
                }

                override fun onFailure(call: Call<StreamerResponse>, t: Throwable) {
                    Log.e(TAG, "Unable to reach Twitch API")
                    Log.e(TAG, t.localizedMessage)
                }

            })

            return ret
        }

    }

}