package com.codebosses.comical.repository.model.chapters
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChapterResult (

	@SerializedName("comic_id") val comic_id : Int,
	@SerializedName("comic_name") val comic_name : String,
	@SerializedName("comic_poster_path") val comic_poster_path : String
): Parcelable