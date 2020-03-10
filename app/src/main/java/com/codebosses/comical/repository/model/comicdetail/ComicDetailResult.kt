package com.codebosses.comical.repository.model.comicdetail
import com.google.gson.annotations.SerializedName


data class ComicDetailResult (

		@SerializedName("details") val details : ComicDetailData,
		@SerializedName("chapters") val chapters : ArrayList<Chapter>
)