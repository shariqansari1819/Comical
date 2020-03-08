package com.codebosses.comical.repository.model.chapters
import com.google.gson.annotations.SerializedName

data class Chapter (

	@SerializedName("status") val status : Boolean,
	@SerializedName("result") val result : List<ChapterResult>
)