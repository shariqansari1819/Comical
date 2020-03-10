package com.codebosses.comical.repository.model.comics
import com.google.gson.annotations.SerializedName

data class Comic (

	@SerializedName("status") val status : Boolean,
	@SerializedName("result") val result : List<ComicResult>
)