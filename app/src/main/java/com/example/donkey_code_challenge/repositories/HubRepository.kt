package com.example.donkey_code_challenge.repositories

import com.example.donkey_code_challenge.model.Hub
import com.example.donkey_code_challenge.network.RetrofitInterface
import com.google.android.gms.maps.model.LatLng
import com.haroldadmin.cnradapter.NetworkResponse
import javax.inject.Inject

class HubRepository @Inject constructor(private val retrofitInterface: RetrofitInterface) {

    suspend fun search(searchQuery: String): List<Hub> {
        when (val res = retrofitInterface.hubSearch(searchQuery)) {
            is NetworkResponse.Success -> return res.body
            is NetworkResponse.ServerError -> TODO()
            is NetworkResponse.NetworkError -> TODO()
            is NetworkResponse.UnknownError -> TODO(res.error.toString())
        }
    }

    suspend fun getNearby(location: LatLng, radius: Int): List<Hub>{
        when(val res = retrofitInterface.nearby(location = "${location.latitude},${location.longitude}", radius = radius)){
            is NetworkResponse.Success -> return res.body.hubs
            is NetworkResponse.ServerError -> TODO()
            is NetworkResponse.NetworkError -> TODO()
            is NetworkResponse.UnknownError -> TODO(res.error.toString())
        }
    }

}