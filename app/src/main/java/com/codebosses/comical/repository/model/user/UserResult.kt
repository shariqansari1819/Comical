package com.codebosses.comical.repository.model.user

import com.google.gson.annotations.SerializedName

data class UserResult (

		@SerializedName("user_id") val user_id : Int,
		@SerializedName("fb_id") val fb_id : String,
		@SerializedName("g_id") val g_id : String,
		@SerializedName("user_name") val user_name : String,
		@SerializedName("user_email") val user_email : String,
		@SerializedName("phone_number") val phone_number : String,
		@SerializedName("profile_image_url") val profile_image_url : String,
		@SerializedName("profile_image_thumb_url") val profile_image_thumb_url : String,
		@SerializedName("password") val password : String,
		@SerializedName("user_role") val user_role : Int,
		@SerializedName("device_id") val device_id : Int,
		@SerializedName("status") val status : Int,
		@SerializedName("date_added") val date_added : String
)