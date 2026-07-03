package academy.nouri.nimbus.data.mapper

import academy.nouri.nimbus.data.remote.model.wallpaper.WallpaperResponse
import academy.nouri.nimbus.domain.model.WallpaperData

fun WallpaperResponse.toWallpaperData(): WallpaperData {
    val randomPath = if (photos.isNullOrEmpty().not()) {
        val randomIndex = photos.indices.random()
        photos[randomIndex]?.src?.original
    } else null

    return WallpaperData(
        wallpaperPath = randomPath
    )
}