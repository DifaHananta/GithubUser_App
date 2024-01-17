package com.dicoding.githubuser.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dicoding.githubuser.data.local.FavoriteUser
import com.dicoding.githubuser.data.local.FavoriteUserDao
import com.dicoding.githubuser.data.local.FavoriteUserDatabase

class FavoriteUserViewModel(application: Application): AndroidViewModel(application) {

    private var mFavoriteUserDao: FavoriteUserDao?
    private var  mFavoriteUserDatabase: FavoriteUserDatabase?

    init {
        mFavoriteUserDatabase = FavoriteUserDatabase.getDatabase(application)
        mFavoriteUserDao = mFavoriteUserDatabase?.favoriteUserDao()
    }

    fun getFavorite(): LiveData<List<FavoriteUser>>? {
        return mFavoriteUserDao?.getFavorite()
    }
}