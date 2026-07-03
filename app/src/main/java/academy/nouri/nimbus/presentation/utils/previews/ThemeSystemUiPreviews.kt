package academy.nouri.nimbus.presentation.utils.previews

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Light",
    uiMode = UI_MODE_NIGHT_NO,
    showSystemUi = true
)
@Preview(
    name = "Dark",
    uiMode = UI_MODE_NIGHT_YES,
    showSystemUi = true
)
annotation class ThemeSystemUiPreviews