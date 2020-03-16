package com.codebosses.comical.repository.model.search

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchResult(

        @SerializedName("comic_id") val comic_id: Int,
        @SerializedName("comic_name") val comic_name: String,
        @SerializedName("comic_banner_path") val comic_banner_path: String,
        @SerializedName("summary") val summary: String,
        @SerializedName("category") val category: String
) : Parcelable