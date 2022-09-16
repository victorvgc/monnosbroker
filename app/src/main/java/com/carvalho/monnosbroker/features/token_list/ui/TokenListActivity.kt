package com.carvalho.monnosbroker.features.token_list.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.carvalho.monnosbroker.R
import com.carvalho.monnosbroker.core.ui.BaseActivity
import com.carvalho.monnosbroker.core.ui.hide
import com.carvalho.monnosbroker.core.ui.show
import com.carvalho.monnosbroker.core.viewmodel.BaseState
import com.carvalho.monnosbroker.databinding.ActivityTokenListBinding
import com.carvalho.monnosbroker.features.coin_details.ui.CoinDetailsActivity
import com.carvalho.monnosbroker.features.token_list.domain.models.TokenItem
import com.carvalho.monnosbroker.features.token_list.ui.token_list.TokenListAdapter
import com.carvalho.monnosbroker.features.token_list.view_models.TokenListViewModel
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TokenListActivity : BaseActivity<List<TokenItem>>() {

    companion object {
        private const val VIEW_ITEM = 1
    }

    override val viewModel: TokenListViewModel by viewModel()

    private val binding: ActivityTokenListBinding by binding(R.layout.activity_token_list)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeStates()

        binding.swipeRefresh.setOnRefreshListener {
            launch {
                viewModel.observeData().collectLatest {
                    binding.swipeRefresh.isRefreshing = false
                    processStates(it)
                }
            }
        }
    }

    override fun processLoadingState(state: BaseState.Loading<List<TokenItem>>) {
        if (state.data != null) {
            //loadList(state.data)
        } else {
            binding.rvTokenList.hide()
            binding.loadingBar.show()
        }
    }

    override fun processSuccessState(state: BaseState.Success<List<TokenItem>>) {
        binding.rvTokenList.show()
        binding.loadingBar.hide()

        loadList(state.data)
    }

    override fun processFailureState(state: BaseState.Failure<List<TokenItem>>) {
        Toast.makeText(this, state.msg, Toast.LENGTH_SHORT).show()
        binding.rvTokenList.hide()
        binding.loadingBar.hide()
    }

    private fun loadList(tokenList: List<TokenItem>) {
        binding.rvTokenList.itemAnimator = SlideInUpAnimator()

        binding.rvTokenList.layoutManager = LinearLayoutManager(this)

        binding.rvTokenList.adapter = TokenListAdapter({ item, position ->
            item.isFavorite = item.isFavorite.not()
            viewModel.toggleFavoriteToken(item)
            (binding.rvTokenList.adapter as TokenListAdapter).updateItemAt(item, position)
        }, { item, _ ->
            Intent(this, CoinDetailsActivity::class.java).apply {
                putExtra(CoinDetailsActivity.COIN_ABBREVIATION, item.currencyAbbreviation)
                putExtra(CoinDetailsActivity.TOKEN_SYMBOL_BASE_CURRENCY, item.currencyIndex)

                startActivityForResult(this, VIEW_ITEM)
            }
        }).apply {
            tokenList.forEach { item ->
                Handler(Looper.getMainLooper()).postDelayed({
                    this.addItem(item)
                }, 10)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == VIEW_ITEM && resultCode == Activity.RESULT_OK) {
            observeStates()
        }
    }
}