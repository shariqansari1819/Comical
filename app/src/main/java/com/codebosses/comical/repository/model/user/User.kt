package com.codebosses.comical.repository.model.user
import com.google.gson.annotations.SerializedName

data class User (

		@SerializedName("status") val status : Boolean,
		@SerializedName("data") val data : UserResult,
		@SerializedName("message") val message : String
)