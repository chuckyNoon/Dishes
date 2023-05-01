package com.example.dishes.ui.list

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dishes.R
import com.example.dishes.common.adapter.ComposableListAdapter
import com.example.dishes.common.arch.AbsFragment
import com.example.dishes.ui.list.model.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DishesFragment : AbsFragment(R.layout.fragment_dishes) {

    private val viewModel: DishesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = requireContext()
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler).apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
        val deleteButton = view.findViewById<Button>(R.id.delete_btn).apply {
            setOnClickListener {
                viewModel.reduce(DishesAction.ClickDelete)
            }
        }
        val progressBar = view.findViewById<ProgressBar>(R.id.pb)
        val contentBlock = view.findViewById<View>(R.id.content)

        val delegates = listOf(
            DishAdapterDelegate(
                layoutInflater,
                requestManager = Glide.with(context),
                onCheckBoxClick = { cell: DishCell ->
                    viewModel.reduce(DishesAction.ClickCheckBox(cellId = cell.id))
                },
                onMoreClick = { cell: DishCell ->
                    viewModel.reduce(DishesAction.ClickMore(cellId = cell.id))
                }
            )
        )

        val adapter = ComposableListAdapter(delegates).also {
            recyclerView.adapter = it
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewStatesFlow().collect { viewState: DishesViewState ->
                    when (viewState) {
                        is DishesViewState.Data -> {
                            progressBar.visibility = View.GONE
                            contentBlock.visibility = View.VISIBLE

                            adapter.submitList(viewState.cells)
                            deleteButton.isEnabled = viewState.isDeleteButtonEnabled

                        }
                        is DishesViewState.Loading -> {
                            progressBar.visibility = View.VISIBLE
                            contentBlock.visibility = View.GONE
                        }
                    }

                }
            }
        }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.eventsFlow().collect { event: DishesEvent? ->
                    when (event) {
                        is DishesEvent.ShowDetails -> {
                            findNavController().navigate(
                                DishesFragmentDirections.actionToDishDetail(event.dishId)
                            )
                        }
                        else -> {
                        }
                    }
                }
            }
        }
    }
}