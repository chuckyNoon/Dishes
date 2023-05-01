package com.example.dishes.ui.list.model

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.example.dishes.R
import com.example.dishes.common.adapter.AbsDelegateViewHolder
import com.example.dishes.common.adapter.AdapterDelegate
import com.example.dishes.common.adapter.DelegateDiffable

data class DishCell(
    val id: String,
    val name: String,
    val price: String,
    val imgUrl: String?,
    val isChecked: Boolean
) : DelegateDiffable<DishCell> {

    override fun isSame(other: DelegateDiffable<*>): Boolean =
        other is DishCell && id == other.id

    override fun getChangePayload(newCell: DishCell): Any = newCell
}

class DishViewHolder(
    parent: ViewGroup,
    inflater: LayoutInflater,
    private val requestManager: RequestManager,
    private val onCheckBoxClick: (DishCell) -> Unit,
    private val onMoreClick: (DishCell) -> Unit
) : AbsDelegateViewHolder<DishCell>(
    inflater.inflate(R.layout.item_dish, parent, false)
) {
    private val nameTextView = itemView.findViewById<TextView>(R.id.name_tv)
    private val priceTextView = itemView.findViewById<TextView>(R.id.price_tv)
    private val imageView = itemView.findViewById<ImageView>(R.id.img)
    private val checkBox = itemView.findViewById<CheckBox>(R.id.cb).apply {
        setOnClickListener {
            latestCell?.let(onCheckBoxClick)
        }
    }

    private val requestOptions = RequestOptions.placeholderOf(R.drawable.ic_launcher_background)

    private var latestCell: DishCell? = null

    init {
        itemView.findViewById<Button>(R.id.more_btn).apply {
            setOnClickListener {
                latestCell?.let(onMoreClick)
            }
        }
    }

    override fun bind(cell: DishCell, payloads: List<Any>?) {
        latestCell = cell

        nameTextView.text = cell.name
        priceTextView.text = cell.price
        checkBox.isChecked = cell.isChecked


        requestManager.load(cell.imgUrl)
            .apply(requestOptions)
            .into(imageView)
    }
}

class DishAdapterDelegate(
    private val inflater: LayoutInflater,
    private val requestManager: RequestManager,
    private val onCheckBoxClick: (DishCell) -> Unit,
    private val onMoreClick: (DishCell) -> Unit
) : AdapterDelegate<DishCell, DishViewHolder> {

    override val cellClass: Class<DishCell> = DishCell::class.java

    override fun onCreateViewHolder(parent: ViewGroup): DishViewHolder =
        DishViewHolder(
            parent,
            inflater,
            requestManager,
            onCheckBoxClick = onCheckBoxClick,
            onMoreClick = onMoreClick
        )

    override fun isUsingCellAsPayload(): Boolean = true
}
