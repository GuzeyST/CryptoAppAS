package ru.guzeyst.cryptoappas.screen.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ru.guzeyst.cryptoappas.screen.detail.CoinDetailActivity
import ru.guzeyst.cryptoappas.POJO.CoinPriceInfo
import ru.guzeyst.cryptoappas.adapters.CoinInfoAdapter
import ru.guzeyst.cryptoappas.databinding.ActivityCoinPriceLsitBinding
import ru.guzeyst.cryptoappas.screen.CoinViewModel

class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoinPriceLsitBinding
    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinPriceLsitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = CoinInfoAdapter(this)
        val rv = binding.rvCoinPriceLsit
        rv.adapter = adapter
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener{
            override fun onCoinClick(coinPriceInfo: CoinPriceInfo) {
                val intent = CoinDetailActivity.newIntent(this@CoinPriceListActivity, coinPriceInfo.FROMSYMBOL)
                startActivity(intent)
            }
        }

        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        viewModel.priceList.observe(this, {
            adapter.coinInfoList = it
        })
    }

}