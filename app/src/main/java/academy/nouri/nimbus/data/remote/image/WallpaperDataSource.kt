package academy.nouri.nimbus.data.remote.image

import academy.nouri.nimbus.data.remote.model.wallpaper.WallpaperResponse
import academy.nouri.nimbus.data.remote.safeCall
import academy.nouri.nimbus.data.utils.Constants.PORTRAIT
import academy.nouri.nimbus.domain.utils.DataError
import academy.nouri.nimbus.domain.utils.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class WallpaperDataSource(private val client: HttpClient) : RemoteWallpaperDataSource {
    override suspend fun getWallpaper(
        query: String,
        isPortrait: Boolean
    ): Result<WallpaperResponse, DataError> {
        return safeCall<WallpaperResponse> {
            client.get("search") {
                parameter("query", query)
                //parameter("color", IMAGE_COLOR)
                if (isPortrait)
                    parameter("orientation", PORTRAIT)
            }
        }
    }
}