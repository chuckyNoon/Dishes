package com.example.dishes.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.dishes.R
import com.example.dishes.common.arch.AbsFragment
import com.example.dishes.ui.detail.model.DishDetailAction
import com.example.dishes.ui.detail.model.DishDetailViewState
import com.example.dishes.ui.list.model.DishesViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DishDetailFragment : AbsFragment(R.layout.fragment_dish_detail) {
    private val args: DishDetailFragmentArgs by navArgs()
    private val viewModel: DishDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = requireContext()

        val nameTextView = view.findViewById<TextView>(R.id.name_tv)
        val priceTextView = view.findViewById<TextView>(R.id.price_tv)
        val descriptionTextView = view.findViewById<TextView>(R.id.description_tv)
        val imageView = view.findViewById<ImageView>(R.id.img)

        val requestManager = Glide.with(context)
        val requestOptions = RequestOptions.placeholderOf(R.drawable.ic_launcher_background)

        viewModel.reduce(DishDetailAction.Init(args.person))

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewStatesFlow().collect { viewState: DishDetailViewState ->
                    nameTextView.text = viewState.name
                    priceTextView.text = viewState.price
                    descriptionTextView.text = viewState.description
                    requestManager
                        .load(viewState.url)
                        .apply(requestOptions)
                        .into(imageView)
                }
            }
        }
    }
}