package com.john.recyclerviewpageindicator.data.api

import com.john.recyclerviewpageindicator.data.model.ApiUser

interface ApiHelper {

    suspend fun getUsers(): List<ApiUser>

    suspend fun getMoreUsers(): List<ApiUser>

    suspend fun getUsersWithError(): List<ApiUser>

}