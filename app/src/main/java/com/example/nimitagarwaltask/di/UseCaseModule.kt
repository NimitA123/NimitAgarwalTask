package com.example.nimitagarwaltask.di

import com.example.nimitagarwaltask.domain.usecase.HoldingsUseCase
import com.example.nimitagarwaltask.domain.usecase.IHoldingsUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    @Singleton
    abstract fun bindHoldingsUseCase(
        impl: HoldingsUseCase
    ): IHoldingsUseCase
}