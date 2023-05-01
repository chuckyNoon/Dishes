package com.example.dishes.data.dish

import com.example.dishes.data.dish.model.Dish

interface DishRepository {
    suspend fun getDishes(): List<Dish>
    suspend fun deleteDish(id: String)
    suspend fun getDishById(id: String): Dish?
}