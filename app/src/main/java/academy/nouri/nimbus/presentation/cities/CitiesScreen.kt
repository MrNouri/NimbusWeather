package academy.nouri.nimbus.presentation.cities

import academy.nouri.nimbus.data.local.database.entity.CityEntity
import academy.nouri.nimbus.domain.model.CityData
import academy.nouri.nimbus.presentation.common_components.TopBar
import academy.nouri.nimbus.presentation.common_components.WeatherImageLoading
import academy.nouri.nimbus.presentation.theme.NimbusTheme
import academy.nouri.nimbus.presentation.theme.swipeGreen
import academy.nouri.nimbus.presentation.theme.swipeRed
import academy.nouri.nimbus.presentation.utils.previews.ComponentPreviews
import academy.nouri.nimbus.presentation.utils.previews.ScreenPreviews
import android.app.Activity
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.CheckBox
import androidx.compose.material.icons.rounded.CheckBoxOutlineBlank
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.LocationCity
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue.EndToStart
import androidx.compose.material3.SwipeToDismissBoxValue.Settled
import androidx.compose.material3.SwipeToDismissBoxValue.StartToEnd
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import coil3.compose.AsyncImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun CitiesScreen(
    state: CitiesState,
    events: Flow<CitiesEvents>,
    onCityNameChange: (String) -> Unit,
    onAction: (CitiesActions) -> Unit,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        events.collect { event ->
            when (event) {
                is CitiesEvents.SuccessSavedToast -> {
                    Toast.makeText(
                        context,
                        "Your city has been saved!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is CitiesEvents.SuccessRemoveToast -> {
                    Toast.makeText(
                        context,
                        "${event.cityName} Removed!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    MainContent(
        onNavigateBack = onNavigateBack,
        state = state,
        onCityNameChange = { onCityNameChange.invoke(it) },
        onAction = onAction
    )
}

@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    state: CitiesState,
    onCityNameChange: (String) -> Unit,
    onAction: (CitiesActions) -> Unit
) {
    val context = LocalContext.current
    val view = LocalView.current

    SideEffect {
        val window = (context as Activity).window
        WindowCompat.getInsetsController(window, view)
            .isAppearanceLightStatusBars = true
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopBar(
            title = "Cities",
            icon = Icons.Rounded.LocationCity,
        ) {
            onNavigateBack.invoke()
        }
        Spacer(Modifier.height(10.dp))
        AddCity(
            modifier = Modifier.padding(horizontal = 16.dp),
            cityName = state.cityName,
            onCityNameChange = onCityNameChange,
            isShowLoading = state.loading
        )
        Spacer(Modifier.height(3.dp))
        RemoteCitiesList(
            isEmpty = state.isEmpty,
            citiesList = state.citiesList,
            onAction = onAction
        )
        Spacer(Modifier.height(20.dp))
        StoreCitiesList(
            modifier = Modifier.padding(horizontal = 16.dp),
            storedCitiesList = state.storedCitiesList,
            imagePath = state.cityWallpapers,
            onAction = onAction
        )
    }
}

@Composable
fun AddCity(
    modifier: Modifier = Modifier,
    cityName: String,
    onCityNameChange: (String) -> Unit,
    isShowLoading: Boolean
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = cityName,
            onValueChange = { onCityNameChange.invoke(it) },
            singleLine = true,
            label = {
                Text(
                    text = "Write city name",
                    fontWeight = FontWeight.W600,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            },
            leadingIcon = {
                if (isShowLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    IconButton(onClick = {
                        onCityNameChange.invoke(cityName)
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = "Search",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            },
            trailingIcon = {
                if (cityName.isNotBlank()) {
                    IconButton(onClick = {
                        onCityNameChange.invoke("")
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.Clear,
                            contentDescription = "remove",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            },
            shape = RoundedCornerShape(8.dp)
        )
    }
}

@Composable
fun RemoteCitiesList(
    modifier: Modifier = Modifier,
    isEmpty: Boolean,
    citiesList: List<CityData>,
    onAction: (CitiesActions) -> Unit
) {
    AnimatedVisibility(
        visible = isEmpty.not(),
        enter = expandVertically() + fadeIn(),
        exit = shrinkVertically() + fadeOut()
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .heightIn(max = 300.dp)
        ) {
            items(citiesList) { city ->
                RemoteCityItem(
                    modifier = Modifier.padding(
                        horizontal = 16.dp,
                        vertical = 5.dp
                    ),
                    cityName = city.name,
                    countryName = city.country,
                    lat = city.lat,
                    lon = city.lon,
                    onAction = onAction
                )
            }
        }
    }
}

@Composable
fun RemoteCityItem(
    modifier: Modifier = Modifier,
    cityName: String,
    countryName: String,
    lat: Double,
    lon: Double,
    onAction: (CitiesActions) -> Unit
) {
    var isShownAddButton by remember { mutableStateOf(true) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .shadow(
                elevation = 3.dp,
                shape = RoundedCornerShape(8.dp),
                clip = false,
                spotColor = MaterialTheme.colorScheme.primary,
                ambientColor = MaterialTheme.colorScheme.primary
            ),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(8.dp)
                    .background(MaterialTheme.colorScheme.primary)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                modifier = Modifier.weight(1f),
                text = "$cityName, $countryName",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 18.sp,
                fontWeight = FontWeight.W500
            )

            AnimatedVisibility(
                visible = isShownAddButton,
                enter = slideInHorizontally(initialOffsetX = { it }),
                exit = slideOutHorizontally(targetOffsetX = { it })
            ) {
                IconButton(
                    onClick = {
                        onAction.invoke(
                            CitiesActions.SaveCity(
                                name = cityName,
                                country = countryName,
                                lat = lat,
                                lon = lon
                            )
                        )
                        isShownAddButton = false
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.AddCircle,
                        contentDescription = "Add city",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}


@Composable
fun StoreCitiesList(
    modifier: Modifier = Modifier,
    storedCitiesList: List<CityEntity>,
    imagePath: Map<String, String>,
    onAction: (CitiesActions) -> Unit
) {
    val itemText = if (storedCitiesList.count() == 1)
        "item"
    else
        "Items"

    AnimatedVisibility(
        visible = storedCitiesList.count() > 0,
        enter = expandVertically() + fadeIn()
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Your stored cities (${storedCitiesList.count()} $itemText)",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W300,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.width(5.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = .3f))
                )
            }
            Spacer(Modifier.height(10.dp))
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(
                    items = storedCitiesList.reversed(),
                    key = { city -> city.city }
                ) { city ->
                    StoredCityItemSwipeable(
                        city = city.city,
                        country = city.county,
                        isSelected = city.isSelected,
                        imagePath = imagePath[city.city],
                        onRemoveCity = {
                            onAction.invoke(
                                CitiesActions.RemoveCity(
                                    name = city.city,
                                    country = city.county,
                                    lat = city.lat,
                                    lon = city.lon
                                )
                            )
                        },
                        onSelectCity = {
                            onAction.invoke(
                                CitiesActions.SelectDefaultCity(
                                    lat = city.lat,
                                    lon = city.lon
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun StoreCityItem(
    modifier: Modifier = Modifier,
    city: String,
    country: String,
    isSelected: Boolean,
    imagePath: String?
) {
    val color = if (isSelected)
        swipeGreen
    else
        MaterialTheme.colorScheme.primary

    var isWallpaperLoading by remember(imagePath) { mutableStateOf(true) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .shadow(
                elevation = 3.dp,
                shape = RoundedCornerShape(8.dp),
                clip = false,
                spotColor = MaterialTheme.colorScheme.primary,
                ambientColor = MaterialTheme.colorScheme.primary
            ),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(8.dp)
                    .background(color)
            )
            Spacer(Modifier.width(10.dp))
            Text(
                modifier = Modifier.weight(1.4f),
                text = "$city, $country",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 20.sp,
                fontWeight = FontWeight.W400,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Box(modifier = Modifier.weight(1f)) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxHeight(),
                    model = imagePath,
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
                        .fillMaxHeight()
                        .width(100.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.surfaceVariant,
                                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = .4f),
                                    Color.Transparent
                                ),
                                startX = 0f,
                                endX = Float.POSITIVE_INFINITY
                            )
                        )
                )

                if (isWallpaperLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        WeatherImageLoading(
                            modifier = Modifier
                                .padding(end = 20.dp)
                                .size(60.dp)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeBackground(
    dismissState: SwipeToDismissBoxState,
    isSelected: Boolean,
) {
    val direction = dismissState.dismissDirection

    val backgroundColor = when (direction) {
        StartToEnd -> swipeGreen
        EndToStart -> swipeRed
        else -> Color.Transparent
    }

    val selectIcon = if (isSelected)
        Icons.Rounded.CheckBox
    else
        Icons.Rounded.CheckBoxOutlineBlank

    Row(
        modifier = Modifier
            .padding(top = 5.dp)
            .fillMaxWidth()
            .height(80.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (direction == StartToEnd || dismissState.currentValue == StartToEnd) {
            Icon(
                imageVector = selectIcon,
                contentDescription = "select",
                tint = Color.White
            )
        } else {
            Spacer(modifier = Modifier.width(48.dp))
        }

        if (direction == EndToStart || dismissState.currentValue == EndToStart) {
            Icon(
                imageVector = Icons.Rounded.Delete,
                contentDescription = "delete",
                tint = Color.White
            )
        } else {
            Spacer(modifier = Modifier.width(48.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoredCityItemSwipeable(
    modifier: Modifier = Modifier,
    city: String,
    country: String,
    isSelected: Boolean,
    imagePath: String?,
    onSelectCity: () -> Unit,
    onRemoveCity: () -> Unit
) {
    val swipeToDismissBoxState = rememberSwipeToDismissBoxState(
        positionalThreshold = { it * .9f }
    )

    LaunchedEffect(swipeToDismissBoxState.currentValue) {
        when (swipeToDismissBoxState.currentValue) {
            EndToStart -> {
                delay(500.milliseconds)
                onRemoveCity()
            }

            StartToEnd -> {
                onSelectCity()
                delay(500.milliseconds)
                swipeToDismissBoxState.snapTo(Settled)
            }

            Settled -> {}
        }
    }

    SwipeToDismissBox(
        state = swipeToDismissBoxState,
        modifier = modifier.fillMaxSize(),
        backgroundContent = {
            SwipeBackground(
                dismissState = swipeToDismissBoxState,
                isSelected = isSelected
            )
        }
    ) {
        StoreCityItem(
            modifier = Modifier.padding(vertical = 5.dp),
            city = city,
            country = country,
            isSelected = isSelected,
            imagePath = imagePath
        )
    }
}

@ComponentPreviews
@Composable
fun PreviewRemoteCityItem() {
    NimbusTheme {
        RemoteCityItem(
            cityName = "Tehran",
            countryName = "Ir",
            lat = 0.0,
            lon = 0.0
        ) {

        }
    }
}

@ScreenPreviews
@Composable
fun PreviewCitiesPage() {
    NimbusTheme {
        CitiesScreen(
            state = CitiesState(),
            events = emptyFlow(),
            onNavigateBack = {},
            onCityNameChange = {},
            onAction = {}
        )
    }
}