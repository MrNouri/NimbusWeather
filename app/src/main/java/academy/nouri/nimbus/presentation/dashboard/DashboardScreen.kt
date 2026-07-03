package academy.nouri.nimbus.presentation.dashboard

import academy.nouri.nimbus.R
import academy.nouri.nimbus.data.utils.Constants.HOST_WEATHER_ICON
import academy.nouri.nimbus.data.utils.Constants.PNG
import academy.nouri.nimbus.domain.model.ForecastData
import academy.nouri.nimbus.domain.model.WeatherData
import academy.nouri.nimbus.domain.model.WeatherDetailItem
import academy.nouri.nimbus.presentation.common_components.LiquidGlassCard
import academy.nouri.nimbus.presentation.common_components.TopBar
import academy.nouri.nimbus.presentation.common_components.WeatherImageLoading
import academy.nouri.nimbus.presentation.common_components.forecast_item.WeatherForecastView
import academy.nouri.nimbus.presentation.common_components.loading.WeatherLoading
import academy.nouri.nimbus.presentation.dashboard.components.AirPollutionView
import academy.nouri.nimbus.presentation.dashboard.components.DayInfo
import academy.nouri.nimbus.presentation.dashboard.utils.analyzeImageBrightness
import academy.nouri.nimbus.presentation.theme.NimbusTheme
import academy.nouri.nimbus.presentation.utils.extensions.enableHazeBorder
import academy.nouri.nimbus.presentation.utils.extensions.enableHazeEffect
import android.app.Activity
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Air
import androidx.compose.material.icons.rounded.ArrowDownward
import androidx.compose.material.icons.rounded.ArrowUpward
import androidx.compose.material.icons.rounded.EmojiEmotions
import androidx.compose.material.icons.rounded.LocationCity
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.WaterDrop
import androidx.compose.material.icons.twotone.LocationOn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import coil3.compose.AsyncImage
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.rememberHazeState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch

@Composable
fun DashboardScreen(
    states: DashboardStates,
    events: Flow<DashboardEvents>,
    onAction: (DashboardActions) -> Unit,
    onNavigateToCitiesPage: () -> Unit,
    paddingValues: PaddingValues = PaddingValues(0.dp)
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        events.collect { event ->
            when (event) {
                is DashboardEvents.AnalyzeWallpaperColor -> {
                    val isDarkness = analyzeImageBrightness(
                        context = context,
                        imagePath = event.imagePath
                    )
                    onAction.invoke(DashboardActions.WallpaperBrightnessAnalyze(isDarkness))
                }

                is DashboardEvents.NavigateToCitiesPage -> {
                    onNavigateToCitiesPage.invoke()
                }
            }
        }
    }

    if (states.hasCity == true) {
        MainContent(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            state = states,
            paddingValues = paddingValues,
            onAction = onAction
        )
    } else {
        EmptyCity {
            onNavigateToCitiesPage.invoke()
        }
    }
}

@Composable
fun EmptyCity(
    modifier: Modifier = Modifier,
    gotoCityPageAction: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopBar(
            title = "Nimbus",
            icon = painterResource(R.drawable.logo),
            onBackClicked = null
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(210.dp),
                painter = painterResource(R.drawable.empty_city),
                contentDescription = "Empty"
            )

            Spacer(Modifier.height(10.dp))

            Text(
                text = "No cities yet!",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )

            Spacer(Modifier.height(40.dp))

            Text(
                text = "Add your first city to get weather\nupdate and daily forecast",
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.W600,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(15.dp))

            ElevatedButton(
                onClick = gotoCityPageAction,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = "Add",
                        tint = Color.White
                    )
                    Text(
                        text = "Add City",
                        color = Color.White,
                        fontWeight = FontWeight.W400,
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(Modifier.height(40.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    modifier = Modifier.size(35.dp),
                    painter = painterResource(R.drawable.swipe_right),
                    contentDescription = "Add",
                    tint = MaterialTheme.colorScheme.primary,
                )

                Spacer(Modifier.width(10.dp))

                Text(
                    text = "Swipe right on a city\nto set it as default.",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    state: DashboardStates,
    paddingValues: PaddingValues,
    onAction: (DashboardActions) -> Unit
) {
    val context = LocalContext.current
    val view = LocalView.current
    val density = LocalDensity.current
    val hazeState = rememberHazeState()
    val coroutineScope = rememberCoroutineScope()

    val minCardHeight = 250.dp
    val maxCardHeight = 570.dp
    val minCardHeightPx = with(density) { minCardHeight.toPx() }
    val maxCardHeightPx = with(density) { maxCardHeight.toPx() }

    val cardHeightAnim = remember { Animatable(minCardHeightPx) }
    val cardHeight = with(density) { cardHeightAnim.value.toDp() }
    val cardProgress = ((cardHeightAnim.value - minCardHeightPx) / (maxCardHeightPx - minCardHeightPx)).coerceIn(0f, 1f)

    val basicWeatherAlpha = (1f - ((cardProgress - 0.53f) / 0.63f)).coerceIn(0f, 1f)

    LaunchedEffect(state.isDarkWallpaper) {
        val window = (context as Activity).window
        WindowCompat.getInsetsController(window, view)
            .isAppearanceLightStatusBars = state.isDarkWallpaper.not()
    }

    if (state.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            WeatherLoading()
        }
    } else {
        Box(
            modifier = modifier.pointerInput(Unit) {
                val velocityTracker = VelocityTracker()
                detectVerticalDragGestures(
                    onDragStart = {
                        coroutineScope.launch { cardHeightAnim.stop() }
                    },
                    onVerticalDrag = { change, dragAmount ->
                        change.consume()
                        velocityTracker.addPosition(
                            change.uptimeMillis,
                            change.position
                        )
                        coroutineScope.launch {
                            cardHeightAnim.snapTo(
                                (cardHeightAnim.value - dragAmount)
                                    .coerceIn(minCardHeightPx, maxCardHeightPx)
                            )
                        }
                    },
                    onDragEnd = {
                        val velocity = velocityTracker.calculateVelocity().y
                        velocityTracker.resetTracking()
                        coroutineScope.launch {
                            val snapTarget = when {
                                velocity < -500f -> maxCardHeightPx
                                velocity > 500f -> minCardHeightPx
                                cardHeightAnim.value > (minCardHeightPx + maxCardHeightPx) / 2f -> maxCardHeightPx
                                else -> minCardHeightPx
                            }

                            cardHeightAnim.animateTo(
                                targetValue = snapTarget,
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioLowBouncy,
                                    stiffness = Spring.StiffnessLow
                                )

                            )
                        }
                    },
                    onDragCancel = {
                        velocityTracker.resetTracking()
                        coroutineScope.launch {
                            cardHeightAnim.animateTo(
                                targetValue = minCardHeightPx,
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessMedium
                                )
                            )
                        }
                    }
                )
            },
            contentAlignment = Alignment.Center
        ) {
            WeatherWallpaper(
                modifier = Modifier.fillMaxSize(),
                wallpaperPath = state.wallpaperPath,
                hazeState = hazeState
            )
            BasicWeatherInfo(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(horizontal = 20.dp)
                    .align(Alignment.TopCenter)
                    .alpha(basicWeatherAlpha),
                weatherData = state.weatherData,
                date = state.currentDateString,
                hazeState = hazeState,
                isMetric = state.isMetric,
                onAction = onAction
            )
            MoreDetailWeather(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(cardHeight),
                weatherData = state.weatherData,
                hazeState = hazeState,
                isDarkBackground = state.isDarkWallpaper,
                quality = state.airPollution,
                forecastList = state.forecastList,
                isMetric = state.isMetric
            )
        }
    }
}

@Composable
fun WeatherWallpaper(
    modifier: Modifier = Modifier,
    wallpaperPath: String?,
    hazeState: HazeState
) {
    var isWallpaperLoading by remember(wallpaperPath) { mutableStateOf(true) }

    if (wallpaperPath != null) {
        Box(modifier = modifier) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .blur(radius = 1.dp)
                    .hazeSource(hazeState),
                model = wallpaperPath,
                contentDescription = "WeatherWallpaper",
                contentScale = ContentScale.Crop,
                placeholder = BrushPainter(
                    Brush.linearGradient(
                        listOf(
                            Color(color = 0xFFFFFFFF),
                            Color(color = 0xFFDDDDDD),
                        )
                    )
                ),
                onLoading = { isWallpaperLoading = true },
                onSuccess = { isWallpaperLoading = false },
                onError = { isWallpaperLoading = false }
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = .2f))
            )

            if (isWallpaperLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    WeatherImageLoading()
                }
            }
        }
    }
}

@Composable
fun BasicWeatherInfo(
    modifier: Modifier = Modifier,
    weatherData: WeatherData?,
    date: String?,
    hazeState: HazeState,
    isMetric: Boolean?,
    onAction: (DashboardActions) -> Unit
) {
    weatherData?.let { weatherData ->
        Column(
            modifier = modifier.padding(top = 30.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                MenuPopup(
                    hazeState = hazeState,
                    isMetric = isMetric ?: true,
                    onAction = onAction
                )
                Spacer(Modifier.width(8.dp))
                Icon(
                    modifier = Modifier.size(30.dp),
                    imageVector = Icons.TwoTone.LocationOn,
                    contentDescription = "Location",
                    tint = Color.White.copy(alpha = .8f)
                )
                Spacer(Modifier.width(5.dp))
                Text(
                    modifier = Modifier.weight(1f),
                    text = "${weatherData.cityName}, ${weatherData.country}",
                    color = Color.White.copy(alpha = .8f),
                    fontWeight = FontWeight.W600,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 22.sp
                )
            }
            Spacer(Modifier.height(25.dp))
            Text(
                modifier = Modifier.padding(start = 5.dp),
                text = date.toString(),
                color = Color.White.copy(alpha = .8f),
                fontWeight = FontWeight.W500,
                fontSize = 16.sp
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${weatherData.temperature.toInt()}°",
                    style = TextStyle(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.White,
                                Color.White.copy(alpha = .8f),
                                Color.White.copy(alpha = .4f),
                                Color.White.copy(alpha = .0f)
                            )
                        ),
                    ),
                    fontWeight = FontWeight.W300,
                    fontSize = 140.sp
                )
                Column(
                    modifier = Modifier.padding(bottom = 35.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        modifier = Modifier.size(75.dp),
                        model = "$HOST_WEATHER_ICON${weatherData.icon}$PNG",
                        contentDescription = "WeatherIcon"
                    )
                    Text(
                        text = weatherData.description,
                        color = Color.White.copy(alpha = .8f),
                        fontWeight = FontWeight.W500,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}

@Composable
fun DetailWeatherInfo(
    modifier: Modifier = Modifier,
    weatherData: WeatherData?,
    hazeState: HazeState,
    isDarkness: Boolean,
    isMetric: Boolean?
) {
    val unit = if (isMetric == true) "°C" else "°F"
    val windUnit = if (isMetric == true) "km/h" else "mph"

    val weatherDetails = remember(weatherData) {
        if (weatherData == null)
            emptyList()
        else
            listOf(
                WeatherDetailItem(
                    label = "Min temp",
                    value = "${weatherData.tempMin.toInt()}",
                    icon = Icons.Rounded.ArrowDownward,
                    unit = unit
                ),
                WeatherDetailItem(
                    label = "Max temp",
                    value = "${weatherData.tempMax.toInt()}",
                    icon = Icons.Rounded.ArrowUpward,
                    unit = unit
                ),
                WeatherDetailItem(
                    label = "Humidity",
                    value = "${weatherData.humidity}",
                    icon = Icons.Rounded.WaterDrop,
                    unit = "%"
                ),
                WeatherDetailItem(
                    label = "Wind",
                    value = "${weatherData.windSpeed}",
                    icon = Icons.Rounded.Air,
                    unit = windUnit
                ),
                WeatherDetailItem(
                    label = "Feel like",
                    value = "${weatherData.feelsLike.toInt()}",
                    icon = Icons.Rounded.EmojiEmotions,
                    unit = unit
                )
            )
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(top = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        weatherDetails.forEach { item ->
            DayInfo(
                hazeState = hazeState,
                item = item,
                isDarkness = isDarkness
            )
        }
    }
}

@Composable
fun AirPollution(
    modifier: Modifier = Modifier,
    quality: Int?
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Air pollution",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.W700
        )
        AirPollutionView(
            airQuality = quality ?: 0
        )
    }
}

@Composable
fun HourlyForecast(
    modifier: Modifier = Modifier,
    forecastList: List<ForecastData>?
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 20.dp),
            text = "Hourly Forecast",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.W700
        )
        Spacer(Modifier.height(12.dp))
        WeatherForecastView(
            items = forecastList ?: emptyList()
        )
    }
}

@Composable
fun MoreDetailWeather(
    weatherData: WeatherData?,
    modifier: Modifier = Modifier,
    hazeState: HazeState,
    isDarkBackground: Boolean,
    quality: Int?,
    forecastList: List<ForecastData>?,
    isMetric: Boolean?
) {
    LiquidGlassCard(
        modifier = modifier,
        hazeState = hazeState,
        roundedCornerSize = 15.dp,
        isDarkness = isDarkBackground,
        isHardness = false
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 5.dp)
        ) {
            DetailWeatherInfo(
                modifier = Modifier.padding(horizontal = 20.dp),
                weatherData = weatherData,
                hazeState = hazeState,
                isDarkness = isDarkBackground,
                isMetric = isMetric
            )
            Spacer(Modifier.height(24.dp))
            AirPollution(
                quality = quality
            )
            Spacer(Modifier.height(50.dp))
            HourlyForecast(forecastList = forecastList)
        }
    }
}

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun MenuPopup(
    modifier: Modifier = Modifier,
    hazeState: HazeState,
    isMetric: Boolean,
    onAction: (DashboardActions) -> Unit
) {
    var isMenuExpanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        IconButton(onClick = { isMenuExpanded = true }) {
            Icon(
                modifier = Modifier.size(30.dp),
                imageVector = Icons.Rounded.Menu,
                contentDescription = "Location",
                tint = Color.White.copy(alpha = .8f)
            )
        }

        DropdownMenu(
            modifier = Modifier
                .width(160.dp)
                .hazeEffect(
                    state = hazeState,
                    style = enableHazeEffect(
                        blurRadius = 5.dp,
                        colorAlpha = .03f
                    )
                )
                .enableHazeBorder(
                    shape = MaterialTheme.shapes.large
                )
                .background(
                    Color.Black.copy(alpha = 0.3f),
                    shape = MaterialTheme.shapes.large
                ),
            shape = MaterialTheme.shapes.large,
            containerColor = Color.Transparent,
            expanded = isMenuExpanded,
            onDismissRequest = { isMenuExpanded = false }
        ) {
            DropdownMenuItem(
                text = {
                    Text(
                        modifier = Modifier.padding(start = 11.dp),
                        text = "Cities",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W500
                    )
                },
                leadingIcon = {
                    Icon(
                        modifier = Modifier.padding(start = 12.dp),
                        imageVector = Icons.Rounded.LocationCity,
                        contentDescription = "Cities",
                        tint = Color.White.copy(alpha = .7f)
                    )
                },
                onClick = {
                    isMenuExpanded = false
                    onAction(DashboardActions.OpenCitiesScreen)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(
                        text = "Metric",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W500
                    )
                },
                leadingIcon = {
                    Checkbox(
                        modifier = Modifier
                            .scale(.93f),
                        checked = isMetric,
                        onCheckedChange = {
                            onAction(DashboardActions.ToggleWeatherUnit(isMetric.not()))
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color.White.copy(alpha = 0.7f),
                            uncheckedColor = Color.White.copy(alpha = 0.4f),
                            checkmarkColor = Color.Black.copy(alpha = .7f)
                        )
                    )
                },
                onClick = {
                    onAction(DashboardActions.ToggleWeatherUnit(isMetric.not()))
                }
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PreviewDashboardScreen() {
    val state = DashboardStates()
    NimbusTheme {
        DashboardScreen(
            states = state,
            events = emptyFlow(),
            onAction = {},
            onNavigateToCitiesPage = {}
        )
    }
}