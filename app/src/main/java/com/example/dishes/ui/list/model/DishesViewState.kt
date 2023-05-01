package com.example.dishes.ui.list.model

import com.example.dishes.common.adapter.DelegateDiffable

sealed class DishesViewState {
    object Loading : DishesViewState()
    data class Data(
        val cells: List<DelegateDiffable<*>>,
        val isDeleteButtonEnabled: Boolean
    ) : DishesViewState()
}
