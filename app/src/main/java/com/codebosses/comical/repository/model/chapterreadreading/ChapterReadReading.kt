package com.codebosses.comical.repository.model.chapterreadreading

import com.codebosses.comical.repository.model.comicdetail.Chapter
import com.google.gson.annotations.SerializedName

data class ChapterReadReading(
        @SerializedName("reading") val reading: ArrayList<Chapter>,
        @SerializedName("read") val read: ArrayList<Chapter>
)