package academy.nouri.nimbus.data.remote.model.wallpaper

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WallpaperResponse(
    @SerialName("next_page")
    val nextPage: String? = null, // https://api.pexels.com/v1/v1/search?page=2&per_page=15&query=nature
    @SerialName("page")
    val page: Int? = null, // 1
    @SerialName("per_page")
    val perPage: Int? = null, // 15
    @SerialName("photos")
    val photos: List<Photo?>? = null,
    @SerialName("total_results")
    val totalResults: Int? = null // 8000
) {
    @Serializable
    data class Photo(
        @SerialName("alt")
        val alt: String? = null, // Explore a serene forest trail with lush greenery and rustic fencing.
        @SerialName("avg_color")
        val avgColor: String? = null, // #514B3B
        @SerialName("height")
        val height: Int? = null, // 6000
        @SerialName("id")
        val id: Int? = null, // 17956020
        @SerialName("liked")
        val liked: Boolean? = null, // false
        @SerialName("photographer")
        val photographer: String? = null, // One Blue Fire Studio
        @SerialName("photographer_id")
        val photographerId: Long? = null, // 309497860
        @SerialName("photographer_url")
        val photographerUrl: String? = null, // https://www.pexels.com/@one-blue-fire-studio-309497860
        @SerialName("src")
        val src: Src? = null,
        @SerialName("url")
        val url: String? = null, // https://www.pexels.com/photo/a-person-walking-down-a-path-in-the-woods-17956020/
        @SerialName("width")
        val width: Int? = null // 4000
    ) {
        @Serializable
        data class Src(
            @SerialName("landscape")
            val landscape: String? = null, // https://images.pexels.com/photos/17956020/pexels-photo-17956020.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=627&w=1200
            @SerialName("large")
            val large: String? = null, // https://images.pexels.com/photos/17956020/pexels-photo-17956020.jpeg?auto=compress&cs=tinysrgb&h=650&w=940
            @SerialName("large2x")
            val large2x: String? = null, // https://images.pexels.com/photos/17956020/pexels-photo-17956020.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940
            @SerialName("medium")
            val medium: String? = null, // https://images.pexels.com/photos/17956020/pexels-photo-17956020.jpeg?auto=compress&cs=tinysrgb&h=350
            @SerialName("original")
            val original: String? = null, // https://images.pexels.com/photos/17956020/pexels-photo-17956020.jpeg
            @SerialName("portrait")
            val portrait: String? = null, // https://images.pexels.com/photos/17956020/pexels-photo-17956020.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=1200&w=800
            @SerialName("small")
            val small: String? = null, // https://images.pexels.com/photos/17956020/pexels-photo-17956020.jpeg?auto=compress&cs=tinysrgb&h=130
            @SerialName("tiny")
            val tiny: String? = null // https://images.pexels.com/photos/17956020/pexels-photo-17956020.jpeg?auto=compress&cs=tinysrgb&dpr=1&fit=crop&h=200&w=280
        )
    }
}