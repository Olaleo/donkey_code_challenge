package com.example.donkey_code_challenge.repositories

import com.example.donkey_code_challenge.model.Hub
import com.example.donkey_code_challenge.network.RetrofitInterface
import com.haroldadmin.cnradapter.NetworkResponse
import javax.inject.Inject

class SearchRepository @Inject constructor(private val retrofitInterface: RetrofitInterface) {

    suspend fun search(searchQuery: String): List<Hub> {
        when (val res = retrofitInterface.hubSearch(searchQuery)) {
            is NetworkResponse.Success -> return res.body
            is NetworkResponse.ServerError -> TODO()
            is NetworkResponse.NetworkError -> TODO()
            is NetworkResponse.UnknownError -> TODO(res.error.toString())
        }
    }

}