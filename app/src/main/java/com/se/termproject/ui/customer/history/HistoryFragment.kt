package com.se.termproject.ui.customer.history

import com.se.termproject.base.kotlin.BaseFragment
import com.se.termproject.databinding.FragmentHistoryBinding
import com.se.termproject.ui.customer.history.HistoryRVAdapter
import com.se.termproject.ui.customer.history.Store

class HistoryFragment : BaseFragment<FragmentHistoryBinding>(FragmentHistoryBinding::inflate) {
    private var storeData = ArrayList<Store>()

    private lateinit var histroyRVAdapter: HistoryRVAdapter

    override fun initAfterBinding() {
        initStore()
        initRecyclerView()
        binding()
    }

    //store data 초기화 - firebase 연동 후 구현
    private fun initStore(){

    }

    //RecyclerView 초기화
    private fun initRecyclerView(){
        histroyRVAdapter = HistoryRVAdapter(storeData)
        binding.rankingRv.adapter = histroyRVAdapter
    }

    private fun binding(){

    }
}