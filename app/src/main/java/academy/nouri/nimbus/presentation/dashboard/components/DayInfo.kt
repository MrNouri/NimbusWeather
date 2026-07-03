package academy.nouri.nimbus.presentation.dashboard.components

import academy.nouri.nimbus.domain.model.WeatherDetailItem
import academy.nouri.nimbus.presentation.common_components.LiquidGlassCard
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.chrisbanes.haze.HazeState

@Composable
fun DayInfo(
    modifier: Modifier = Modifier,
    hazeState: HazeState,
    size: Dp = 65.dp,
    item: WeatherDetailItem,
    isDarkness: Boolean
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LiquidGlassCard(
            modifier = modifier
                .size(size),
            hazeState = hazeState,
            roundedCornerSize = 80.dp,
            isDarkness = isDarkness,
            isHardness = true
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = item.value,
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.W900
                )
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = item.icon,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
        Spacer(Modifier.height(5.dp))
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W700
                    )
                ) {
                    append(item.label)
                }
                if (item.unit.isNotEmpty()) {
                    append("\n")
                    withStyle(
                        style = SpanStyle(
                            color = Color.White.copy(alpha = .9f),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.W400
                        )
                    ) {
                        append("(${item.unit})")
                    }
                }
            },
            textAlign = TextAlign.Center,
            lineHeight = 15.sp
        )
    }
}