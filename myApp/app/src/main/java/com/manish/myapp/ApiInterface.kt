package com.manish.myapp

import com.manish.myapp.MyDataItem
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("posts")
    fun getData(): Call<List<MyDataItem>>
}