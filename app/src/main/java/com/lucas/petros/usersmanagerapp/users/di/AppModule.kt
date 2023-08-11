package com.lucas.petros.usersmanagerapp.users.di

import com.lucas.petros.network.getRetrofit
import com.lucas.petros.usersmanagerapp.users.data.remote.service.Api
import com.lucas.petros.usersmanagerapp.users.data.repository.MyRepository
import com.lucas.petros.usersmanagerapp.users.data.repository.MyRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApi(): Api {
        return getRetrofit()
            .create(Api::class.java)
    }

    @Provides
    @Singleton
    fun provideMyRepository(api: Api): MyRepository {
        return MyRepositoryImpl(api)
    }
}