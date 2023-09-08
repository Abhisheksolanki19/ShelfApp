package com.abhishek.shelfapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Book(
    val id: String,
    val image: String,
    val hits: Long,
    val alias: String,
    val title: String,
    val lastChapterDate: Long,
    @Transient
    var isFavourite: Boolean = false,
) : Parcelable
