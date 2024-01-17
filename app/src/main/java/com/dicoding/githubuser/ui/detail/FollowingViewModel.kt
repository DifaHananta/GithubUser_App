package com.dicoding.githubuser.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.data.response.ItemsItem
import com.dicoding.githubuser.data.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel: ViewModel() {
    val listFollowing = MutableLiveData<ArrayList<ItemsItem>>()

    fun setListFollowing(username: String) {
        RetrofitClient.apiInstance
            .getFollowing(username)
            .enqueue(object : Callback<ArrayList<ItemsItem>> {
                override fun onResponse(
                    call: Call<ArrayList<ItemsItem>>,
                    response: Response<ArrayList<ItemsItem>>
                ) {
                    if (response.isSuccessful) {
                        listFollowing.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<ItemsItem>>, t: Throwable) {
                    Log.d("Failure", t.message!!)
                }

            })
    }

    fun getListFollowing(): LiveData<ArrayList<ItemsItem>>{
        return listFollowing
    }
}