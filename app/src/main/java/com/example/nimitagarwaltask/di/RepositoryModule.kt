package com.example.nimitagarwaltask.di


import com.example.nimitagarwaltask.data.repository_imp.HoldingsRepositoryImpl
import com.example.nimitagarwaltask.domain.repository.HoldingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindHoldingsRepository(
        impl: HoldingsRepositoryImpl
    ): HoldingsRepository
}