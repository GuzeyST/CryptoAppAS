package ru.guzeyst.cryptoappas.screen.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import ru.guzeyst.cryptoappas.databinding.ActivityCoinDetailBinding
import ru.guzeyst.cryptoappas.screen.CoinViewModel

class CoinDetailActivity : AppCompatActivity() {
    private lateinit var viewModel: CoinViewModel
    private lateinit var binding: ActivityCoinDetailBinding

    companion object{
        private const val KEY_FSYM = "fsym"
        fun newIntent(context: Context, fromSymbol:String): Intent{
            val intent = Intent(context, CoinDetailActivity::class.java)
            intent.putExtra(KEY_FSYM, fromSymbol)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(!intent.hasExtra(KEY_FSYM)) {
            finish()
            return
        }
        val fromSymbol = intent.getStringExtra(KEY_FSYM)

        fromSymbol?.let {fsym ->
            viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
            viewModel.getDetailInfo(fsym).observe(this, {
                binding.apply {
                    tvFromSymbol.text = it.FROMSYMBOL
                    tvCurrentPrice.text = it.PRICE
                    tvMin.text = it.LOWDAY
                    tvMax.text = it.HIGHDAY
                    tvLastUse.text = it.LASTMARKET
                    tvLastUpdate.text = it.getFormatedTime()
                    Picasso.get().load(it.getFullImageUrl()).into(ivLabelCoin)
                }
            })
        }
    }
}