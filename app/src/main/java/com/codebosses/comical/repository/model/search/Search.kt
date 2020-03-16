package com.codebosses.comical.repository.model.search

import com.google.gson.annotations.SerializedName

data class Search(@SerializedName("status") val status: Boolean,
                  @SerializedName("result") val result: List<SearchResult>)