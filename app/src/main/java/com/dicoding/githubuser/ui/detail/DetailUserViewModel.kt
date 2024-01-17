package com.dicoding.githubuser.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.githubuser.data.local.FavoriteUser
import com.dicoding.githubuser.data.local.FavoriteUserDao
import com.dicoding.githubuser.data.local.FavoriteUserDatabase
import com.dicoding.githubuser.data.response.UserDetailResponse
import com.dicoding.githubuser.data.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class DetailUserViewModel(application: Application): AndroidViewModel(application) {
    val user = MutableLiveData<UserDetailResponse>()

    private var mFavoriteUserDao: FavoriteUserDao?
    private var  mFavoriteUserDatabase: FavoriteUserDatabase?
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        mFavoriteUserDatabase = FavoriteUserDatabase.getDatabase(application)
        mFavoriteUserDao = mFavoriteUserDatabase?.favoriteUserDao()
    }

    fun setUserDetail(username: String) {
        RetrofitClient.apiInstance
            .getDetailUser(username)
            .enqueue(object : Callback<UserDetailResponse> {
                override fun onResponse(
                    call: Call<UserDetailResponse>,
                    response: Response<UserDetailResponse>
                ) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                    Log.d("Failure", t.message!!)
                }

            })
    }

    fun getUserDetail(): LiveData<UserDetailResponse> {
        return user
    }

    fun addFavorite(id: Int, username: String, avatar: String) {
        executorService.execute {
            val user = FavoriteUser(id, username, avatar)
            mFavoriteUserDao?.addFavorite(user)
        }
    }

    suspend fun checkFavorite(id: Int) = mFavoriteUserDao?.checkFavorite(id)

    fun removeFavorite(id: Int) {
        executorService.execute {
            mFavoriteUserDao?.removeFavorite(id)
        }
    }
}