package com.android.dailyrounds.model

data class Book(
    val id: String,
    val image: String,
    val popularity: Int,
    val publishedChapterDate: Int,
    val score: Double,
    val title: String,
)