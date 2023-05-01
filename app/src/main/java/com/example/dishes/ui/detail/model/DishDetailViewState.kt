package com.example.dishes.ui.detail.model

data class DishDetailViewState(
    val name: String?,
    val description: String?,
    val price: String?,
    val url: String?
) {
    companion object {
        val INITIAL = DishDetailViewState(
            name = null,
            description = null,
            price = null,
            url = null
        )
    }
}