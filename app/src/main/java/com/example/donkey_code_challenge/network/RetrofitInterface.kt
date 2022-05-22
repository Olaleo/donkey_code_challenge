package com.example.donkey_code_challenge.network

import com.example.donkey_code_challenge.model.ErrorModel
import com.example.donkey_code_challenge.model.Hub
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RetrofitInterface {

    @GET("owners/admins/1090/hubs/search")
    suspend fun hubSearch(
        @Query("query") query: String,
        @Header("Accept:application/com.donkeyrepublic.v2") header: String = ""
    ): NetworkResponse<List<Hub>, ErrorModel>

}