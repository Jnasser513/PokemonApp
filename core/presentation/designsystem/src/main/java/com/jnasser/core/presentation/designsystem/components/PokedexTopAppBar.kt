@file:OptIn(ExperimentalMaterial3Api::class)

package com.jnasser.core.presentation.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jnasser.core.presentation.designsystem.R
import com.jnasser.core.presentation.designsystem.theme.PokemonAppTheme
import com.jnasser.core.presentation.designsystem.theme.PokemonColors

/**
 * Configuration data class for customizing the behavior and appearance of [PokedexTopAppBar].
 */
data class PokedexTopAppBarConfig(
    val title: String,
    val titleColor: Color = PokemonColors.BlueDark,
    val navigationIcon: PokedexTopAppBar.NavigationIcon?,
    val actions: List<PokedexTopAppBar.Action> = emptyList(),
    val centerTitle: Boolean = false,
    val color: Color? = null,
    val scrollBehavior: TopAppBarScrollBehavior? = null
)

/**
 * Container object for reusable TopAppBar-related UI components,
 * including custom navigation icons and actions.
 */
object PokedexTopAppBar {

    /**
     * Represents an action shown in the top app bar, either as an icon or text.
     */
    data class Action(
        val text: String,
        val icon: ImageVector? = null,
        val enabled: Boolean = true,
        val tintColor: Color? = null,
        val onClick: () -> Unit = {}
    )

    /**
     * Sealed class for defining navigation icons with behavior and style.
     */
    sealed class NavigationIcon(
        val iconVectorDrawable: Int = R.drawable.ic_arrow_left,
        val iconTint: Color = PokemonColors.BlueDark,
        val onClick: () -> Unit
    ) {
        data class Up(val click: () -> Unit) : NavigationIcon(
            iconVectorDrawable = R.drawable.ic_arrow_left,
            iconTint = PokemonColors.BlueDark,
            onClick = click
        )
    }
}

/**
 * Custom TopAppBar for the Pokedex app. Supports optional navigation icon,
 * actions (icons or text), centered title, and scroll behavior.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokedexTopAppBar(
    modifier: Modifier = Modifier,
    config: PokedexTopAppBarConfig
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = config.title,
                color = config.titleColor,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = if (config.centerTitle) TextAlign.Center else null
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent
        ),
        navigationIcon = {
            config.navigationIcon?.let {
                IconButton(onClick = it.onClick) {
                    Icon(
                        imageVector = ImageVector.vectorResource(it.iconVectorDrawable),
                        tint = it.iconTint,
                        contentDescription = null
                    )
                }
            }
        },
        actions = {
            // Render icon actions
            config.actions.filter { it.icon != null }.forEach { action ->
                IconButton(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(40.dp)
                        .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)),
                    onClick = action.onClick,
                    enabled = action.enabled
                ) {
                    Icon(
                        imageVector = action.icon ?: Icons.AutoMirrored.Default.KeyboardArrowLeft,
                        contentDescription = null,
                        tint = action.tintColor ?: LocalContentColor.current
                    )
                }
            }

            // Render text actions
            config.actions.filter { it.icon == null }.forEach { action ->
                TextButton(
                    onClick = action.onClick,
                    enabled = action.enabled
                ) {
                    Text(
                        text = action.text,
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = action.tintColor ?: LocalContentColor.current
                        )
                    )
                }
            }
        },
        scrollBehavior = config.scrollBehavior
    )
}

@Preview
@Composable
private fun PokedexTopAppBarPreview() {
    PokemonAppTheme {
        PokedexTopAppBar(
            config = PokedexTopAppBarConfig(
                title = "Charizard",
                centerTitle = false,
                navigationIcon = PokedexTopAppBar.NavigationIcon.Up {},
                actions = listOf(
                    PokedexTopAppBar.Action(
                        text = "#006",
                        tintColor = PokemonColors.Gray300
                    )
                )
            )
        )
    }
}
