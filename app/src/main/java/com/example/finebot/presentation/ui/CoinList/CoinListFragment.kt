package com.example.finebot.presentation.ui.CoinList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.binance.api.client.BinanceApiClientFactory
import com.binance.api.client.domain.market.*
import com.example.finebot.R
import com.example.finebot.domain.model.Coin
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.io.Closeable
import java.math.BigDecimal

@AndroidEntryPoint
class CoinListFragment : Fragment() {

    private val coinList = ArrayList<Coin>()
    private val realTimePrices = ArrayList<Coin>()
    private var adapter = CoinListAdapter()
    private lateinit var closable : Closeable
    val client = BinanceApiClientFactory.newInstance().newWebSocketClient()
    companion object {
        fun newInstance() = CoinListFragment()
        private val TAG="CoinListFragment"
        var priceMap=HashMap<String,BigDecimal>()
    }

    private lateinit var viewModel: CoinListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.coin_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<RecyclerView>(R.id.rvCoins).adapter=adapter
        view.findViewById<RecyclerView>(R.id.rvCoins).layoutManager= LinearLayoutManager(context)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CoinListViewModel::class.java)
         viewModel.getCoins()
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
                    client.onAggTradeEvent(i.name.toString().toLowerCase()){
                       it.let {
                           priceMap[it.symbol]=it.price.toBigDecimal()
                       }

                    }

                }
            }

                /*   client.onCandlestickEvent("ethusdt",CandlestickInterval.ONE_MINUTE){
            Log.i("TAG",it.toString())
        }*/
            }


        }


    override fun onResume() {
        super.onResume()
        Log.i(TAG,"OnResume")
    }



    override fun onPause() {
        super.onPause()
        Log.i(TAG,"OnPause")

    }

    override fun onDestroy() {
        Log.i(TAG,"OnDestroy")
        super.onDestroy()
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG,"OnStop")

    }

}