package com.dicoding.githubuser.data.response

import com.google.gson.annotations.SerializedName

data class GithubResponse(

	@field:SerializedName("items")
	val items: ArrayList<ItemsItem>
)

data class ItemsItem(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

)
