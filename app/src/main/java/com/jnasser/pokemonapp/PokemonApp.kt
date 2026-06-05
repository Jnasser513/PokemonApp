package com.jnasser.pokemonapp

import android.app.Application
import com.jnasser.core.data.di.coreDataModule
import com.jnasser.core.database.di.coreDatabaseModule
import com.jnasser.pokemon.network.di.pokemonNetworkModule
import com.jnasser.pokemon.presentation.com.jnasser.pokemon.presentation.di.pokemonPresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class PokemonApp: Application() {

    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        startKoin {
            androidLogger()
            androidContext(this@PokemonApp)
            modules(
                coreDataModule,
                coreDatabaseModule,
                pokemonPresentationModule,
                pokemonNetworkModule
            )
        }
    }
}