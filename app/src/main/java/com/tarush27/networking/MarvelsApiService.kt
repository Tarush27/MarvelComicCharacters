package com.tarush27.networking

import com.tarush27.marvelConstants.API_KEY
import com.tarush27.marvelConstants.hash
import com.tarush27.marvelConstants.timeStamp
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelsApiService {
    @GET("v1/public/characters")
    fun getCharacters(
        @Query("ts") time: String = timeStamp,
        @Query("apikey") apiKey: String = API_KEY,
        @Query("hash") hashKey: String = hash()
    ): Call<MarvelCharacterResponse>

}