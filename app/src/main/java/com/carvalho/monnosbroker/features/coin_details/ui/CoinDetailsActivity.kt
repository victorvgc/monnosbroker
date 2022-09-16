package com.carvalho.monnosbroker.features.coin_details.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import com.carvalho.monnosbroker.R
import com.carvalho.monnosbroker.core.ui.BaseActivity
import com.carvalho.monnosbroker.core.ui.hide
import com.carvalho.monnosbroker.core.ui.setOnceClickListener
import com.carvalho.monnosbroker.core.ui.show
import com.carvalho.monnosbroker.core.viewmodel.BaseState
import com.carvalho.monnosbroker.databinding.ActivityCoinDetailsBinding
import com.carvalho.monnosbroker.features.coin_details.domain.models.CoinDetailsViewData
import com.carvalho.monnosbroker.features.coin_details.view_models.CoinDetailsViewModel
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import org.koin.androidx.viewmodel.ext.android.viewModel

class CoinDetailsActivity : BaseActivity<CoinDetailsViewData>() {

    companion object {
        const val COIN_ABBREVIATION = "coin"
        const val TOKEN_SYMBOL_BASE_CURRENCY = "token"
    }

    private val binding: ActivityCoinDetailsBinding by binding(R.layout.activity_coin_details)

    override val viewModel: CoinDetailsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeStates(intent.extras)

        binding.ivShare.setOnceClickListener {
            val share = Intent.createChooser(Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/html"
                putExtra(
                    Intent.EXTRA_TEXT,
                    Html.fromHtml(
                        "<p>Give <a href=\"${viewModel.getCoinUrl()}\">${viewModel.getCoinName()}</a> a look!</p>",
                        Html.TO_HTML_PARAGRAPH_LINES_INDIVIDUAL
                    )
                )
            }, null)
            startActivity(Intent.createChooser(share, null))
        }

        binding.ivFav.setOnceClickListener {
            binding.apply {
                coin = viewModel.toggleFav()

                if (viewModel.hasModification()) {
                    setResult(Activity.RESULT_OK)
                } else {
                    setResult(Activity.RESULT_CANCELED)
                }
            }.executePendingBindings()
        }
    }

    override fun processLoadingState(state: BaseState.Loading<CoinDetailsViewData>) {
        binding.loadingBar.show()
    }

    override fun processSuccessState(state: BaseState.Success<CoinDetailsViewData>) {
        binding.loadingBar.hide()
        binding.apply {
            baseCoin = state.data.baseCoin
            coin = state.data.coin
            token = state.data.token
        }.executePendingBindings()

        if (state.data.coin.totalSupply != 0.0) {
            val circulating = state.data.coin.circulatingSupply / state.data.coin.totalSupply
            val total = 100.0f - circulating.toFloat()

            val entries = mutableListOf<PieEntry>()
            entries.add(PieEntry(circulating.toFloat(), "Circulating supply"))
            entries.add(PieEntry(total, "Total supply"))

            val pieSet = PieDataSet(entries, "Supply")
            val pieData = PieData(pieSet)

            binding.supplyChart.data = pieData
            binding.supplyChart.setTouchEnabled(false)
            binding.supplyChart.invalidate()
        } else {
            binding.chartCard.hide()
        }
    }

    override fun processFailureState(state: BaseState.Failure<CoinDetailsViewData>) {
        binding.loadingBar.hide()
        Toast.makeText(this, state.msg, Toast.LENGTH_SHORT).show()
    }
}