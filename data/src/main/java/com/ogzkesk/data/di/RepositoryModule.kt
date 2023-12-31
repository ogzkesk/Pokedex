package com.ogzkesk.data.di

import com.ogzkesk.data.repository.PokedexRepository
import com.ogzkesk.data.repository.PokedexRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {


    @Binds
    @ViewModelScoped
    fun bindPokedexRepository(
        pokedexRepositoryImpl: PokedexRepositoryImpl,
    ): PokedexRepository

}