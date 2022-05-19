package com.se.termproject.ui.customer.home

import androidx.recyclerview.widget.GridLayoutManager
import com.se.termproject.R
import com.se.termproject.base.kotlin.BaseFragment
import com.se.termproject.databinding.FragmentHomeBinding
import com.se.termproject.ui.customer.home.MarketRVAdapter.Market
import com.se.termproject.ui.customer.home.MarketRVAdapter.MarketRVAdapter

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private var marketData = ArrayList<Market>()

    override fun initAfterBinding() {
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
        val marketRVAdapter = MarketRVAdapter(marketData)
        binding.homeMarketsRecyclerView.adapter = marketRVAdapter
        binding.homeMarketsRecyclerView.layoutManager = GridLayoutManager(context, 3)

        marketRVAdapter.setMyItemClickListner(object : MarketRVAdapter.MyItemClickListner{
            override fun onItemClick(market: Market) {
                //여기다가 아이템 클리했을때 일어나는 일 적어주면 됩니다~
            }
        })
    }
}