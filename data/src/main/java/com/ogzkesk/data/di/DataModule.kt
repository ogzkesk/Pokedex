package com.ogzkesk.data.di

import com.ogzkesk.data.remote.PokedexService
import com.ogzkesk.data.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Duration

@Module
@InstallIn(ViewModelComponent::class)
object DataModule{

    @Provides
    @ViewModelScoped
    fun provideOkHttp() : OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(Duration.ofSeconds(10))
            .connectTimeout(Duration.ofSeconds(10))
            .build()

    @Provides
    @ViewModelScoped
    fun providePokemonService(client: OkHttpClient) : PokedexService =
        Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PokedexService::class.java)

}
