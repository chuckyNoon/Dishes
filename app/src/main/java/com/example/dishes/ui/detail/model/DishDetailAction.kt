package com.example.dishes.ui.detail.model

sealed class DishDetailAction {
    data class Init(val dishId: String) : DishDetailAction()
}