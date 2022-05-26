package com.se.termproject.ui.customer.home

import android.annotation.SuppressLint
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.se.termproject.R
import com.se.termproject.base.kotlin.BaseFragment
import com.se.termproject.data.Shop
import com.se.termproject.databinding.FragmentHomeBinding
import com.se.termproject.ui.customer.home.adapter.ShopRVAdapter
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private var shops = ArrayList<Shop>()

    private lateinit var shopRVAdapter: ShopRVAdapter
    private lateinit var mPopupWindow: PopupWindow

    // after onCreate()
    override fun initAfterBinding() {
        initRecyclerView()
    }

    // initialize RecyclerView
    private fun initRecyclerView() {
        shopRVAdapter = ShopRVAdapter(shops)
        binding.homeMarketsRecyclerView.adapter = shopRVAdapter
        binding.homeMarketsRecyclerView.layoutManager = GridLayoutManager(context, 3)

        shopRVAdapter.setMyItemClickListner(object : ShopRVAdapter.MyItemClickListner{
            override fun onItemClick(shop: Shop) {
                openPopupWindow(shop)
            }
        })
    }

    // open PopupWindow for market information
    @SuppressLint("InflateParams")
    private fun openPopupWindow(shop: Shop) {
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

        // kakao map api
        initKakaoMapApi()

        // data
        popupView.findViewById<TextView>(R.id.popup_window_market_name_tv).text = shop.name
    }

    // initialize kakao map api
    private fun initKakaoMapApi() {
        val mapView = MapView(context)
        val container = mPopupWindow.contentView.findViewById<RelativeLayout>(R.id.popup_window_market_location_map_container)
        container.addView(mapView)

        // change center point
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(0.0, 0.0), true);

        // change zoom level
        mapView.setZoomLevel(7, true)

        // zoom in
        mapView.zoomIn(true)

        // zoom out
        mapView.zoomOut(true)

        // add marker
        val marker = MapPOIItem()
        mapView.addPOIItem(marker)
    }
}
