package com.example.final_project.domain.montir

import com.example.final_project.domain.montir.model.MontirResponeMessage
import com.example.final_project.domain.montir.model.NearbyMontirResponeMessage
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface MontirAPI {
    @GET("montir/find/nearby")
    fun findNearbyMontir(
        @Header("Authorization") token: String,
        @Query("lat") lat: String,
        @Query("long") long: String
    ): Call<NearbyMontirResponeMessage>

    @GET("montir/profile/detail/{id}")
    fun getMontirDetailById(@Header("Authorization") token: String, @Path("id") id: String): Call<MontirResponeMessage>
}