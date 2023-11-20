package com.ogzkesk.data.di

import com.ogzkesk.data.repository.pref.DataRepository
import com.ogzkesk.data.repository.pref.DatastoreRepositoryImpl
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
    fun bindPreferencesRepository(
        datastoreRepositoryImpl: DatastoreRepositoryImpl
    ) : DataRepository


}