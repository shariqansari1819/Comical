package com.codebosses.comical.repository.model.comicdetail

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Chapter(

    @SerializedName("chapter_id") val chapter_id: Int,
    @SerializedName("chapter_name") val chapter_name: String,
    @SerializedName("poster_path") val poster_path: String
): Parcelable