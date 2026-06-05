package com.jnasser.pokemonapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.jnasser.core.presentation.designsystem.theme.PokemonAppTheme
import com.jnasser.pokemonapp.navigation.NavigationRoot

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokemonAppTheme {
                val navController = rememberNavController()
                NavigationRoot(
                    navController = navController
                )
            }
        }
    }
}