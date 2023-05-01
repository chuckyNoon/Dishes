package com.example.dishes.data.dish.model

data class Dish(
    val id: String,
    val name: String,
    val description: String?,
    val image: String?,
    val price: Int,
)