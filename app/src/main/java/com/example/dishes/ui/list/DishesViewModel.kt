package com.example.dishes.ui.list

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.dishes.common.arch.AbsViewModel
import com.example.dishes.common.adapter.DelegateDiffable
import com.example.dishes.common.replace
import com.example.dishes.data.dish.DishRepository
import com.example.dishes.domain.priceformatter.PriceFormatter
import com.example.dishes.ui.list.model.DishCell
import com.example.dishes.ui.list.model.DishesAction
import com.example.dishes.ui.list.model.DishesEvent
import com.example.dishes.ui.list.model.DishesViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DishesViewModel @Inject constructor(
    private val dishRepository: DishRepository,
    private val priceFormatter: PriceFormatter
) : AbsViewModel<DishesViewState, DishesAction, DishesEvent>(DishesViewState.Loading) {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val cells = dishRepository.getDishes()
                .map {
                    DishCell(
                        id = it.id,
                        name = it.name,
                        price = priceFormatter.format(it.price),
                        imgUrl = it.image,
                        isChecked = false
                    )
                }
            updateViewState { DishesViewState.Data(cells, isDeleteButtonEnabled = false) }
        }
    }

    override fun reduce(action: DishesAction) {
        when (action) {
            is DishesAction.ClickCheckBox -> handleClickCheckBox(action)
            is DishesAction.ClickDelete -> handleClickDelete()
            is DishesAction.ClickMore -> handleClickMore(action)
        }
    }

    private fun handleClickCheckBox(action: DishesAction.ClickCheckBox) {
        viewModelScope.launch {
            val viewState = viewState
            require(viewState is DishesViewState.Data)

            val clickedCell = viewState.cells
                .filterIsInstance<DishCell>()
                .firstOrNull { it.id == action.cellId }
                ?: return@launch
            val isChecked = clickedCell.isChecked
            val newCells =
                viewState.cells.replace(clickedCell, clickedCell.copy(isChecked = !isChecked))
            updateViewState {
                DishesViewState.Data(
                    cells = newCells,
                    isDeleteButtonEnabled = shouldEnabledDeleteButton(newCells)
                )
            }
            dishRepository.deleteDish(action.cellId)
        }
    }

    private fun handleClickDelete() {
        viewModelScope.launch {
            val viewState = viewState
            require(viewState is DishesViewState.Data)

            val (deletedCells, newCells) = viewState.cells.partition { it is DishCell && it.isChecked }
            updateViewState {
                DishesViewState.Data(
                    cells = newCells,
                    isDeleteButtonEnabled = shouldEnabledDeleteButton(newCells)
                )
            }
            withContext(Dispatchers.IO) {
                deletedCells.forEach {
                    val id = (it as DishCell).id
                    dishRepository.deleteDish(id)
                }
            }
        }
    }

    private fun handleClickMore(action: DishesAction.ClickMore) {
        event = DishesEvent.ShowDetails(action.cellId)
    }

    private fun shouldEnabledDeleteButton(cells: List<DelegateDiffable<*>>) =
        cells.any { it is DishCell && it.isChecked }
}