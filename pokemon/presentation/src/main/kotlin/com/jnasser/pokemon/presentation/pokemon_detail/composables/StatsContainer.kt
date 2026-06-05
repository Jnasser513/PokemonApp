package com.jnasser.pokemon.presentation.pokemon_detail.composables

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.jnasser.core.presentation.designsystem.extensions.toPastel
import com.jnasser.core.presentation.designsystem.theme.PokemonColors
import com.jnasser.pokemon.presentation.R
import com.jnasser.pokemon.presentation.pokemon_detail.model.StatContainerDataUi

@Composable
fun StatsContainer(
    modifier: Modifier = Modifier,
    statContainerDataUi: List<StatContainerDataUi>,
    color: Color
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.stats),
            style = MaterialTheme.typography.titleMedium,
            color = PokemonColors.BlueDark
        )
        Spacer(Modifier.height(10.dp))
        statContainerDataUi.forEach { stat ->
            StatData(stat, color)
        }
    }
}

@Composable
fun StatData(
    stat: StatContainerDataUi,
    color: Color
) {
    val progress = stat.value / stat.maxValue.toFloat()

    val animatedProgress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        animatedProgress.snapTo(0f)
        animatedProgress.animateTo(
            targetValue = progress,
            animationSpec = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
        )
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(3f),
            text = stat.name,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = MaterialTheme.typography.labelLarge,
            color = PokemonColors.Gray400
        )

        LinearProgressIndicator(
            modifier = Modifier
                .weight(5f)
                .height(16.dp),
            progress = { animatedProgress.value },
            color = color,
            drawStopIndicator = {},
            gapSize = (-10).dp,
            strokeCap = StrokeCap.Round,
            trackColor = color.toPastel(0.8f),
        )

        Text(
            modifier = Modifier.padding(horizontal = 4.dp).weight(1.2f),
            text = stat.value.toString().padStart(3, '0'),
            style = MaterialTheme.typography.bodyMedium,
            color = PokemonColors.Gray400
        )
    }
}