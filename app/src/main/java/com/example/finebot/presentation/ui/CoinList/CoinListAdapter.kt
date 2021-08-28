package com.example.finebot.presentation.ui.CoinList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.finebot.R
import com.example.finebot.domain.model.Coin
import com.example.finebot.presentation.ui.CoinList.CoinListFragment.Companion.priceMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.math.BigDecimal

class CoinListAdapter() : RecyclerView.Adapter<CoinListAdapter.CoinListViewHolder>() {

    private var context: Context? = null
    val DIFF_CALLBACK: DiffUtil.ItemCallback<Coin> = object : DiffUtil.ItemCallback<Coin>() {
        override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return oldItem.name == oldItem.name
        }

        override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return false
        }

    }
    private val differ: AsyncListDiffer<Coin> = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinListViewHolder {
        context = parent.context
        return CoinListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_coin,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CoinListViewHolder, position: Int) {
        val coin = differ.currentList[position]

        holder.nameTextView.text = coin.name

        CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                delay(300)
                CoroutineScope(Dispatchers.Main).launch {
                    if (valid(priceMap[holder.nameTextView.text.toString()]) &&
                        valid2(holder.priceTextView?.text?.toString())
                        && holder.priceTextView.text.toString().toBigDecimal()
                        > priceMap[holder.nameTextView.text?.toString()]
                    ) {
                        holder.priceTextView.background =
                            ContextCompat.getDrawable(context!!, R.drawable.rounded_price_red)
                    } else if (valid(priceMap[holder.nameTextView.text.toString()]) &&
                        valid2(holder.priceTextView?.text?.toString())
                        && holder.priceTextView.text.toString().toBigDecimal()
                        < priceMap[holder.nameTextView.text?.toString()]
                    ) {
                        holder.priceTextView.background = ContextCompat.getDrawable(
                            context!!,
                            R.drawable.rounded_price_green
                        )
                    }

                    holder.priceTextView.text =
                      priceMap[holder.nameTextView.text].toString()

                }
            }
        }
    }

    private fun valid2(toString: String?): Boolean {
        if(toString=="null" || toString.isNullOrEmpty())
            return false
        else
            return true

    }

    private fun valid(i: BigDecimal?): Boolean {
        if (i == null)
            return false
        else
            return true
    }

    fun submitList(list: ArrayList<Coin>) {
        differ.submitList(list)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    class CoinListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView = view.findViewById<TextView>(R.id.tvCoinName)
        val priceTextView = view.findViewById<TextView>(R.id.tvCoinPrice)
    }
}