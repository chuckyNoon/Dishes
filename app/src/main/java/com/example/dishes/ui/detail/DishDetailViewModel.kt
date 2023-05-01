package com.example.dishes.ui.detail

import androidx.lifecycle.viewModelScope
import com.example.dishes.common.arch.AbsViewModel
import com.example.dishes.data.dish.DishRepository
import com.example.dishes.domain.priceformatter.PriceFormatter
import com.example.dishes.ui.detail.model.DishDetailAction
import com.example.dishes.ui.detail.model.DishDetailEvent
import com.example.dishes.ui.detail.model.DishDetailViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DishDetailViewModel @Inject constructor(
    private val dishRepository: DishRepository,
    private val priceFormatter: PriceFormatter
) :
    AbsViewModel<DishDetailViewState, DishDetailAction, DishDetailEvent>(DishDetailViewState.INITIAL) {

    override fun reduce(action: DishDetailAction) {
        when (action) {
            is DishDetailAction.Init -> handleInitAction(action)
        }
    }

    private fun handleInitAction(action: DishDetailAction.Init) {
        viewModelScope.launch(Dispatchers.IO) {
            val dish = dishRepository.getDishById(action.dishId) ?: return@launch
            updateViewState {
                it.copy(
                    name = dish.name,
                    description = dish.description,
                    price = priceFormatter.format(dish.price),
                    url = dish.image
                )
            }
        }
    }
}