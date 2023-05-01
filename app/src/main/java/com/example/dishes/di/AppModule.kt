package com.example.dishes.di

import com.example.dishes.data.dish.DishRepository
import com.example.dishes.data.dish.FakeDishRepository
import com.example.dishes.domain.priceformatter.PriceFormatter
import com.example.dishes.domain.priceformatter.PriceFormatterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    abstract fun bindPriceFormatter(priceFormatter: PriceFormatterImpl): PriceFormatter

    @Binds
    abstract fun bindDishRepository(dishRepository: FakeDishRepository): DishRepository
}