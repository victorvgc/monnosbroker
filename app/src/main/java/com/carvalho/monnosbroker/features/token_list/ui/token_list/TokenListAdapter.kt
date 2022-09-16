package com.carvalho.monnosbroker.features.token_list.ui.token_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.carvalho.monnosbroker.R
import com.carvalho.monnosbroker.core.ui.BaseAdapter
import com.carvalho.monnosbroker.core.ui.hide
import com.carvalho.monnosbroker.core.ui.setOnceClickListener
import com.carvalho.monnosbroker.core.ui.show
import com.carvalho.monnosbroker.databinding.ItemTokenBinding
import com.carvalho.monnosbroker.features.token_list.domain.models.TokenItem

class TokenListAdapter(
    private val onFavClick: (item: TokenItem, position: Int) -> Unit,
    private val onItemClick: (item: TokenItem, position: Int) -> Unit
) : BaseAdapter<TokenItem>(
    R.layout.item_token,
    onItemClick
) {

    companion object {
        private const val LAST_ITEM = -1
        private const val NORMAL_ITEM = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<TokenItem> {
        val binding: ItemTokenBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            layoutId, parent, false
        )
        if (viewType == LAST_ITEM)
            binding.ivDivider.hide()
        else
            binding.ivDivider.show()

        return TokenListViewHolder(binding, onFavClick = onFavClick, onItemClick = onItemClick)
    }

    override fun getItemViewType(position: Int): Int {
        return if (itemList.size - 1 == position)
            LAST_ITEM
        else
            NORMAL_ITEM
    }

    class TokenListViewHolder(
        private val binding: ItemTokenBinding,
        private val onFavClick: (item: TokenItem, position: Int) -> Unit,
        private val onItemClick: (item: TokenItem, position: Int) -> Unit,

        ) : BaseViewHolder<TokenItem>(
        binding,
        onItemClick
    ) {
        override fun bind(item: TokenItem, position: Int) {
            binding.root.setOnceClickListener { onItemClick(item, position) }
            binding.ivFavorite.setOnceClickListener { onFavClick(item, position) }

            binding.apply {
                this.item = item
            }.executePendingBindings()
        }
    }
}