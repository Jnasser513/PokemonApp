package com.jnasser.core.presentation.designsystem.enums

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.jnasser.core.presentation.designsystem.R
import com.jnasser.core.presentation.designsystem.theme.PokemonColors

enum class PokemonTypeEnum(
    @StringRes val label: Int,
    val color: Color,
    @DrawableRes val icon: Int
) {
    NORMAL(R.string.type_normal, PokemonColors.TypeNormal, R.drawable.ic_pokeball),
    FIGHTING(R.string.type_fighting, PokemonColors.TypeFighting, R.drawable.ic_pokeball),
    FLYING(R.string.type_flying, PokemonColors.TypeFlying, R.drawable.ic_pokeball),
    POISON(R.string.type_poison, PokemonColors.TypePoison, R.drawable.ic_pokeball),
    GROUND(R.string.type_ground, PokemonColors.TypeGround, R.drawable.ic_pokeball),
    ROCK(R.string.type_rock, PokemonColors.TypeRock, R.drawable.ic_pokeball),
    BUG(R.string.type_bug, PokemonColors.TypeBug, R.drawable.ic_pokeball),
    GHOST(R.string.type_ghost, PokemonColors.TypeGhost, R.drawable.ic_pokeball),
    STEEL(R.string.type_steel, PokemonColors.TypeSteel, R.drawable.ic_pokeball),
    FIRE(R.string.type_fire, PokemonColors.TypeFire, R.drawable.ic_pokeball),
    WATER(R.string.type_water, PokemonColors.TypeWater, R.drawable.ic_pokeball),
    GRASS(R.string.type_grass, PokemonColors.TypeGrass, R.drawable.ic_pokeball),
    ELECTRIC(R.string.type_electric, PokemonColors.TypeElectric, R.drawable.ic_pokeball),
    PSYCHIC(R.string.type_psychic, PokemonColors.TypePsychic, R.drawable.ic_pokeball),
    ICE(R.string.type_ice, PokemonColors.TypeIce, R.drawable.ic_pokeball),
    DRAGON(R.string.type_dragon, PokemonColors.TypeDragon, R.drawable.ic_pokeball),
    DARK(R.string.type_dark, PokemonColors.TypeDark, R.drawable.ic_pokeball),
    FAIRY(R.string.type_fairy, PokemonColors.TypeFairy, R.drawable.ic_pokeball),
    STELLAR(R.string.type_stellar, PokemonColors.TypeStellar, R.drawable.ic_pokeball),
    UNKNOWN(R.string.type_unknown, PokemonColors.TypeUnknown, R.drawable.ic_pokeball);

    companion object {
        fun fromName(name: String): PokemonTypeEnum {
            return entries.firstOrNull { it.name.equals(name, ignoreCase = true) } ?: UNKNOWN
        }
    }
}
