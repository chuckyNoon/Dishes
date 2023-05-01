package com.example.dishes.ui.list.model

sealed class DishesEvent {
    data class ShowDetails(val dishId: String) : DishesEvent()
}