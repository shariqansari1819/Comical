package com.codebosses.comical.repository.model.comicdetail
import com.google.gson.annotations.SerializedName


data class ComicDetail (

	@SerializedName("status") val status : Boolean,
	@SerializedName("result") val result : ComicDetailResult
)