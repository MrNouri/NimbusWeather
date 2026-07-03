package academy.nouri.nimbus.data.remote.image

import academy.nouri.nimbus.data.remote.model.wallpaper.WallpaperResponse
import academy.nouri.nimbus.domain.utils.DataError
import academy.nouri.nimbus.domain.utils.Result

interface RemoteWallpaperDataSource {
    suspend fun getWallpaper(
        query: String,
        isPortrait: Boolean
    ): Result<WallpaperResponse, DataError>
}
