package com.jnasser.core.presentation.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ChipColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jnasser.core.presentation.designsystem.enums.PokemonTypeEnum
import com.jnasser.core.presentation.designsystem.extensions.toPastel
import com.jnasser.core.presentation.designsystem.theme.PokemonAppTheme
import com.jnasser.core.presentation.designsystem.theme.PokemonColors

data class PokedexChipConfig(
    val type: PokemonTypeEnum,
    val onClick: () -> Unit,
)

@Composable
fun PokedexChip(
    modifier: Modifier = Modifier,
    config: PokedexChipConfig,
) {
    AssistChip(
        modifier = Modifier.padding(horizontal = 5.dp),
        onClick = config.onClick,
        label = {
            Text(
                text = stringResource(config.type.label),
                style = MaterialTheme.typography.labelSmall,
                color = config.type.color
            )
        },
        leadingIcon = {
            Icon(
                modifier = Modifier.size(AssistChipDefaults.IconSize),
                imageVector = ImageVector.vectorResource(config.type.icon),
                tint = config.type.color,
                contentDescription = null
            )
        },
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(
            width = 1.dp,
            color = config.type.color
        ),
        colors = ChipColors(
            containerColor = config.type.color.toPastel(),
            leadingIconContentColor = config.type.color,
            labelColor = config.type.color,
            disabledContainerColor = PokemonColors.Gray100,
            trailingIconContentColor = config.type.color,
            disabledLabelColor = PokemonColors.Gray100,
            disabledLeadingIconContentColor = PokemonColors.Gray100,
            disabledTrailingIconContentColor = PokemonColors.Gray100
        )
    )
}

@Preview
@Composable
private fun PokedexChipPreview() {
    PokemonAppTheme {
        PokedexChip(
            config = PokedexChipConfig(
                type = PokemonTypeEnum.FIRE
            ){}
        )
    }
}