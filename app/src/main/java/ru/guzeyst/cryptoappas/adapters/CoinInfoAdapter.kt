package ru.guzeyst.cryptoappas.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.guzeyst.cryptoappas.POJO.CoinPriceInfo
import ru.guzeyst.cryptoappas.R
import ru.guzeyst.cryptoappas.databinding.ItemCoinInfoBinding

class CoinInfoAdapter(private val context: Context) : RecyclerView.Adapter<CoinInfoAdapter.CoinViewHolder>() {

    var coinInfoList: List<CoinPriceInfo> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var onCoinClickListener: OnCoinClickListener? = null

    class CoinViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemCoinInfoBinding.bind(itemView)

        fun bind(coinInfo: CoinPriceInfo, context: Context) = with(binding) {
            val symbolsTemplate = context.resources.getString(R.string.symbol_tamplate)
            val lastUpdateTemplate = context.resources.getString(R.string.last_update_template)
            tvSymbols.text = String.format(symbolsTemplate, coinInfo.FROMSYMBOL, coinInfo.TOSYMBOL)
            tvPrice.text = coinInfo.PRICE
            tvTimeUpdate.text = String.format(lastUpdateTemplate, coinInfo.getFormatedTime())
            Picasso.get().load(coinInfo.getFullImageUrl()).into(imLogoCoin)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_coin_info, parent, false)
        return CoinViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        holder.bind(coinInfoList[position], context)
        holder.itemView.setOnClickListener{
            onCoinClickListener?.onCoinClick(coinInfoList[position])
        }
    }

    override fun getItemCount() = coinInfoList.size

    interface OnCoinClickListener{
        fun onCoinClick(coinPriceInfo: CoinPriceInfo)
    }
}