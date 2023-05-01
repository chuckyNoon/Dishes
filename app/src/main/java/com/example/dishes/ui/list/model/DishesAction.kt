package com.example.dishes.ui.list.model

sealed class DishesAction {
    data class ClickCheckBox(val cellId: String) : DishesAction()
    data class ClickMore(val cellId: String) : DishesAction()
    object ClickDelete : DishesAction()
}