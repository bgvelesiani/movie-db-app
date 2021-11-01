package com.gvelesiani.movieapp.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Movie(
    val isAdultRated: Boolean,
    val imageUrl: String?,
    val movieId: Int,
    val movieTitle: String,
    val movieRating: Double,
    val movieReleaseDate: String,
    val movieDescription: String,
    val movieVoteCount: Int,
    val movieStatus: String
) : Parcelable