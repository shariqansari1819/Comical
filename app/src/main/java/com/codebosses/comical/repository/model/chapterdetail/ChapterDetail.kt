package com.codebosses.comical.repository.model.chapterdetail
import com.codebosses.comical.repository.model.chapterdetail.ChapterDetailResult
import com.google.gson.annotations.SerializedName


data class ChapterDetail (

	@SerializedName("status") val status : Boolean,
	@SerializedName("result") val result : ChapterDetailResult
)