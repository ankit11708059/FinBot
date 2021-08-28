package com.example.finebot.presentation.ui.Patterns

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.binance.api.client.domain.market.Candlestick
import com.example.finebot.R
import com.example.finebot.domain.model.Coin
import com.example.finebot.presentation.ui.CoinList.CoinListFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.math.BigDecimal

class PatternAdapter() : RecyclerView.Adapter<PatternAdapter.PatternViewHolder>() {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatternViewHolder {
        context = parent.context
        return PatternViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_coin,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PatternViewHolder, position: Int) {
        val coin = differ.currentList[position]
        holder.nameTextView.text = coin.name
        CoroutineScope(Dispatchers.Main).launch {
            while(true) {
                delay(400)
                if(PatternsFragment.candlestickList[holder.nameTextView.text.toString()]?.size ?:0 > 2) {
                   val list= PatternsFragment.candlestickList[holder.nameTextView.text.toString()]
                    if(list?.get(list.size-3)?.let { !isGreen(it) }==true
                        && list?.get(list.size-2)?.let { !isGreen(it) }==true){
                     /*  if(list?.get(list.size-1)?.open.toBigDecimal() > list?.get(list.size-2).close.toBigDecimal()
                           && list?.get(list.size-1)?.close.toBigDecimal() < list?.get(list.size-2).open.toBigDecimal()){
                           holder.patternTextView.text="SELL"
                       }*/
                        holder.patternTextView.text="SELL"
                       // Log.i("PatternAdapter ",holder.nameTextView.text.toString())
                    }
                    else{
                        holder.patternTextView.text=""
                    }

                    if(holder.nameTextView.text.toString() == "ADAUSDT"){
                       // Log.i("PatternAdapter",list.toString())
                    }
                }
            }
        }

    }

    private fun isGreen(candlestick: Candlestick): Boolean {
     if(candlestick.open.toBigDecimal() < candlestick.close.toBigDecimal()){
         return true
     }
        else
            return false
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


    class PatternViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView = view.findViewById<TextView>(R.id.tvCoinName)
        val patternTextView = view.findViewById<TextView>(R.id.tvCoinPrice)
    }
}