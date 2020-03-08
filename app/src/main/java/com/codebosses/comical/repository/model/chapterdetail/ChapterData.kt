package com.codebosses.comical.repository.model.chapterdetail
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChapterData(

	@SerializedName("comic_id") val comic_id : Int,
	@SerializedName("category") val category : String,
	@SerializedName("comic_name") val comic_name : String,
	@SerializedName("author") val author : String,
	@SerializedName("comic_status") val comic_status : String,
	@SerializedName("comic_poster_path") val comic_poster_path : String,
	@SerializedName("rating") val rating : Double,
	@SerializedName("comic_banner_path") val comic_banner_path : String,
	@SerializedName("comic_genre") val comic_genre : String,
	@SerializedName("comic_release_date") val comic_release_date : Int,
	@SerializedName("summary") val summary : String,
	@SerializedName("tags") val tags : String,
	@SerializedName("date_added") val date_added : String,
	@SerializedName("date_updated") val date_updated : String,
	@SerializedName("status") val status : Int,
	@SerializedName("added_by") val added_by : Int
): Parcelable