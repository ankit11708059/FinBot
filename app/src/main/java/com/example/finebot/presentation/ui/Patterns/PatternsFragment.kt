package com.example.finebot.presentation.ui.Patterns

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.binance.api.client.BinanceApiClientFactory
import com.binance.api.client.domain.market.Candlestick
import com.binance.api.client.domain.market.CandlestickInterval
import com.example.finebot.R
import com.example.finebot.databinding.PatternsFragmentBinding
import com.example.finebot.domain.model.Coin
import com.example.finebot.presentation.ui.CoinList.CoinListAdapter
import com.example.finebot.presentation.ui.CoinList.CoinListFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class PatternsFragment : Fragment(R.layout.patterns_fragment) {

    companion object {
        fun newInstance() = PatternsFragment()
        var candlestickList= HashMap<String,ArrayList<Candlestick>>()
        private val TAG="PatternsFragment"
    }
    private var adapter = PatternAdapter()
    private val coinList = ArrayList<Coin>()
    private var patternsFragmentBinding : PatternsFragmentBinding?=null
    private lateinit var viewModel: PatternsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = PatternsFragmentBinding.bind(view)
        patternsFragmentBinding = binding
        view.findViewById<RecyclerView>(R.id.rvCoins).adapter=adapter
        view.findViewById<RecyclerView>(R.id.rvCoins).layoutManager= LinearLayoutManager(context)
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PatternsViewModel::class.java)
        viewModel.getCoins()
        val client = BinanceApiClientFactory.newInstance().newWebSocketClient()
        lifecycleScope.launchWhenStarted {
            viewModel.users.collect {
                for (i in it) {
                    val r = i
                    if (r.symbol?.contains("USDT") == true) {
                        coinList.add(Coin(name = r.symbol))
                        adapter.submitList(coinList)
                    }
                }
                for (i in coinList) {
                    client.onCandlestickEvent(i.name.toString().toLowerCase(),CandlestickInterval.ONE_MINUTE) {
                        it.let {
                            val candlestick = Candlestick()
                            candlestick.open=it.open
                            candlestick.close=it.close
                            candlestick.high=it.high
                            candlestick.low=it.low
                            val size : Int= candlestickList[it.symbol]?.size?:0
                          //  Log.i(TAG,candlestick.open+" "+it.open)
                            if(size > 0){
                                val c = candlestickList[it.symbol]
                                if(size > 1 && c?.get(c.size-2)?.open!=c?.get(c.size-1)?.open) {
                                    if(it.symbol == "ADAUSDT"){
                                        Log.i("PatternAdapter",candlestick.toString())
                                    }
                                    candlestickList[it.symbol]?.add(candlestick)
                                }
                                else if(size==1){
                                    candlestickList[it.symbol]?.add(candlestick)
                                }
                                if(candlestickList[it.symbol]?.size?:0 > 4){
                                    candlestickList[it.symbol]?.removeAt(0)
                                }
                            }
                            else {
                                var temp = ArrayList<Candlestick>()
                                temp.add(candlestick)
                                candlestickList[it.symbol]=temp
                              //  Log.i(TAG, candlestickList.size.toString())
                               // val sizey: Int = candlestickList[it.symbol]?.size ?: 0
                              //  Log.i(TAG,sizey.toString())
                            }
                        }

                    }

                }
            }

            /*   client.onCandlestickEvent("ethusdt",CandlestickInterval.ONE_MINUTE){
        Log.i("TAG",it.toString())
    }*/
        }

    }

}