package com.se.termproject.ui.customer.home

import android.annotation.SuppressLint
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.GridLayoutManager
import com.se.termproject.R
import com.se.termproject.base.kotlin.BaseFragment
import com.se.termproject.databinding.FragmentHomeBinding
import com.se.termproject.data.Market
import com.se.termproject.ui.customer.home.adapter.MarketRVAdapter

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private var marketData = ArrayList<Market>()

    private lateinit var marketRVAdapter: MarketRVAdapter
    private lateinit var mPopupWindow: PopupWindow

    override fun initAfterBinding() {
        initData()
        initRecyclerView()
    }

    private fun initData() {
        if (marketData.isEmpty()) {
            marketData.apply{
                add(Market("호식당",R.drawable.ic_logo))
                add(Market("태평돈가스",R.drawable.ic_logo))
                add(Market("맘보",R.drawable.ic_logo))
                add(Market("푸드타운",R.drawable.ic_logo))
                add(Market("행복로드",R.drawable.ic_logo))
                add(Market("내가 반한 닭",R.drawable.ic_logo))
                add(Market("코뿡하우스",R.drawable.ic_logo))
                add(Market("육연차",R.drawable.ic_logo))
                add(Market("신의 한컵",R.drawable.ic_logo))
            }
        }
    }

    private fun initRecyclerView() {
        marketRVAdapter = MarketRVAdapter(marketData)
        binding.homeMarketsRecyclerView.adapter = marketRVAdapter
        binding.homeMarketsRecyclerView.layoutManager = GridLayoutManager(context, 3)

        marketRVAdapter.setMyItemClickListner(object : MarketRVAdapter.MyItemClickListner{
            override fun onItemClick(market: Market) {
                openPopupWindow(market)
            }
        })
    }

    @SuppressLint("InflateParams")
    private fun openPopupWindow(market: Market) {
        val size = activity?.windowManager?.currentWindowMetricsPointCompat()
        val width = ((size?.x ?: 0) * 0.8f).toInt()
        val height = ((size?.y ?: 0) * 0.4f).toInt()

        val inflater = activity?.getSystemService(AppCompatActivity.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.popup_window_market, null)
        mPopupWindow = PopupWindow(popupView, width, WindowManager.LayoutParams.WRAP_CONTENT)

        mPopupWindow.apply {
            animationStyle = 1
            isFocusable = true
            isOutsideTouchable = true
            showAtLocation(popupView, Gravity.CENTER, 0, -100)
            setOnDismissListener {
                binding.homeBackgroudV.visibility = View.INVISIBLE
            }
        }
        binding.homeBackgroudV.visibility = View.VISIBLE

        // data
        popupView.findViewById<TextView>(R.id.popup_window_market_name_tv).text = market.name
    }
}