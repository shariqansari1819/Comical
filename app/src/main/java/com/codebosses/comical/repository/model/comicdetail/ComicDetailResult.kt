package com.codebosses.comical.repository.model.comicdetail
import com.google.gson.annotations.SerializedName

data class ComicDetailResult (

	@SerializedName("chapter_id") val chapter_id : Int,
	@SerializedName("comic_id") val comic_id : Int,
	@SerializedName("chapter_name") val chapter_name : String,
	@SerializedName("images_url_array") val images_url_array : String,
	@SerializedName("poster_path") val poster_path : String,
	@SerializedName("date_added") val date_added : String,
	@SerializedName("date_updated") val date_updated : String,
	@SerializedName("status") val status : Int,
	@SerializedName("added_by") val added_by : Int
)