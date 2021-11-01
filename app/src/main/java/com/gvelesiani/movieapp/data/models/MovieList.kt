package com.gvelesiani.movieapp.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class MovieList(
    @SerializedName("page")
    var page: Int,
    @SerializedName("results")
    var movieDtoResults: List<MovieDto>,
    @SerializedName("total_pages")
    var totalPages: Int,
    @SerializedName("total_results")
    var totalResults: Int
)

@Parcelize
class MovieDto(
    @SerializedName("adult")
    val isAdultRated: Boolean,
    @SerializedName("poster_path")
    val imageUrl: String?,
    @SerializedName("id")
    val movieId: Int,
    @SerializedName("title")
    val movieTitle: String,
    @SerializedName("vote_average")
    val movieRating: Double,
    @SerializedName("release_date")
    val movieReleaseDate: String,
    @SerializedName("overview")
    val movieDescription: String,
    @SerializedName("vote_count")
    val movieVoteCount: Int,
    @SerializedName("original_language")
    val movieStatus: String
) : Parcelable