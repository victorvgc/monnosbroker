package com.carvalho.monnosbroker.features.ui

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
import com.carvalho.monnosbroker.features.domain.models.TokenItem
import com.carvalho.monnosbroker.features.ui.token_list.TokenListAdapter
import com.carvalho.monnosbroker.features.view_models.TokenListViewModel
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TokenListActivity : BaseActivity<List<TokenItem>>() {

    override val viewModel: TokenListViewModel by viewModel()

    private val binding: ActivityTokenListBinding by binding(R.layout.activity_token_list)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.rvTokenList.itemAnimator = SlideInUpAnimator()

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
        binding.rvTokenList.hide()
        binding.loadingBar.show()
    }

    override fun processSuccessState(state: BaseState.Success<List<TokenItem>>) {
        binding.rvTokenList.show()
        binding.loadingBar.hide()

        binding.rvTokenList.layoutManager = LinearLayoutManager(this)

        binding.rvTokenList.adapter = TokenListAdapter({ item, position ->
            item.isFavorite = item.isFavorite.not()
            viewModel.toggleFavoriteToken(item)
            (binding.rvTokenList.adapter as TokenListAdapter).updateItemAt(item, position)
        }, { item, _ ->
            // start details activity
            Toast.makeText(this, "Open details of ${item.currencyName}", Toast.LENGTH_SHORT).show()
        }).apply {
            state.data.forEach { item ->
                Handler(Looper.getMainLooper()).postDelayed({
                    this.addItem(item)
                }, 10)
            }
        }
    }

    override fun processFailureState(state: BaseState.Failure<List<TokenItem>>) {
        Toast.makeText(this, state.msg, Toast.LENGTH_SHORT).show()
        binding.rvTokenList.hide()
        binding.loadingBar.hide()
    }


}