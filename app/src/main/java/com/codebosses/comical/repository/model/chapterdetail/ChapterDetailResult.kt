package com.codebosses.comical.repository.model.chapterdetail
import com.google.gson.annotations.SerializedName


data class ChapterDetailResult (

		@SerializedName("details") val details : ChapterData,
		@SerializedName("chapters") val chapters : ArrayList<Comics>
)