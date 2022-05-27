package com.se.termproject.ui.customer.home

import android.annotation.SuppressLint
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.database.*
import com.se.termproject.R
import com.se.termproject.base.kotlin.BaseFragment
import com.se.termproject.data.Customer
import com.se.termproject.data.Review
import com.se.termproject.data.Shop
import com.se.termproject.databinding.FragmentHomeBinding
import com.se.termproject.util.ApplicationClass.Companion.USER_ID
import com.se.termproject.util.getUserId
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    companion object {
        private const val TAG = "FRAG/HOME"
    }

    private var shops = ArrayList<Shop>()
    private var shopIdx: Int = 0
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    private lateinit var selectedShop: Shop
    private lateinit var homeRVAdapter: HomeRVAdapter
    private lateinit var mPopupWindow: PopupWindow

    // firebase
    private lateinit var mDatabase: FirebaseDatabase
    private lateinit var mShopsReference: DatabaseReference
    private lateinit var mCustomersReference: DatabaseReference
    private lateinit var mCustomerReference: DatabaseReference

    // after onCreate()
    override fun initAfterBinding() {

        mDatabase = FirebaseDatabase.getInstance()
        mCustomerReference = mDatabase.getReference("customers")

        initReference()
        USER_ID = getUserId()!!
        homeRVAdapter = HomeRVAdapter(requireContext()) // initialize RecyclerView adapter

        initRecyclerView()
        binding.homeJjymSaveBtn.setOnClickListener {


            val current = LocalDateTime.now();
            val formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초")
            val formatted = current.format(formatter)
            val review =
                Review(selectedShop.name, binding.homeJjymMemoEt.text.toString(), formatted)
            mCustomersReference.child(USER_ID).child("review").setValue(review)

//            mCustomerReference.child(USER_ID).child("Review").child(formatted).child("shop_name").setValue("가게_이름")
//            mCustomerReference.child(USER_ID).child("Review").child(formatted).child("user_review").setValue(binding.homeJjymMemoTv.text.toString())
            binding.homeJjymCl.visibility = View.INVISIBLE
        }
    }

    // initialize firebase reference
    private fun initReference() {
        mDatabase = FirebaseDatabase.getInstance()
        mShopsReference = mDatabase.getReference("shops")
        mCustomersReference = mDatabase.getReference("customers")

        // get data from realtime database
        mShopsReference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                shops.clear()

                for (data in dataSnapshot.children) {
                    val shop = data.getValue(Shop::class.java)!!
                    shops.add(shop)
                }

                homeRVAdapter.addData(shops)
            }

            override fun onCancelled(databaseError: DatabaseError) {}

        })

        mCustomersReference.child(USER_ID).addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshop: DataSnapshot) {
                val customer = dataSnapshop.getValue(Customer::class.java)!!
                Toast.makeText(requireContext(), "작성 완료", Toast.LENGTH_SHORT).show()

                Log.d(TAG, "customer's review: ${customer.review}")
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    // initialize RecyclerView
    private fun initRecyclerView() {
        binding.homeMarketsRecyclerView.adapter = homeRVAdapter
        binding.homeMarketsRecyclerView.layoutManager = GridLayoutManager(context, 3)

        homeRVAdapter.setMyItemClickListner(object : HomeRVAdapter.MyItemClickListner {
            override fun onItemClick(shop: Shop, position: Int) {
                selectedShop = shop
                shopIdx = position
                latitude = shop.latitude
                longitude = shop.longitude

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

        val inflater =
            activity?.getSystemService(AppCompatActivity.LAYOUT_INFLATER_SERVICE) as LayoutInflater
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

        // data binding
        popupView.findViewById<TextView>(R.id.popup_window_market_name_tv).text = selectedShop.name
        popupView.findViewById<TextView>(R.id.popup_window_total_table_count_tv).text =
            selectedShop.totalTableCount.toString()
        popupView.findViewById<TextView>(R.id.popup_window_available_table_count_tv).text =
            selectedShop.availableTableCount.toString()

        // data
        popupView.findViewById<ImageView>(R.id.popup_window_market_jjym_not_activate_icon_iv)
            .setOnClickListener {
                binding.homeJjymCl.visibility = View.VISIBLE
                popupView.findViewById<ImageView>(R.id.popup_window_market_jjym_not_activate_icon_iv).visibility =
                    View.GONE
                popupView.findViewById<ImageView>(R.id.popup_window_market_jjym_activate_icon_iv).visibility =
                    View.VISIBLE
                mPopupWindow.dismiss()
            }

        popupView.findViewById<ImageView>(R.id.popup_window_market_jjym_activate_icon_iv)
            .setOnClickListener {
                binding.homeJjymCl.visibility = View.INVISIBLE
                popupView.findViewById<ImageView>(R.id.popup_window_market_jjym_not_activate_icon_iv).visibility =
                    View.VISIBLE
                popupView.findViewById<ImageView>(R.id.popup_window_market_jjym_activate_icon_iv).visibility =
                    View.GONE
            }

        binding.homeJjymActivateIconIv.setOnClickListener {
            binding.homeJjymCl.visibility = View.INVISIBLE
            popupView.findViewById<ImageView>(R.id.popup_window_market_jjym_not_activate_icon_iv).visibility =
                View.VISIBLE
            popupView.findViewById<ImageView>(R.id.popup_window_market_jjym_activate_icon_iv).visibility =
                View.GONE
        }

        val availableTableCount = selectedShop.availableTableCount
        val totalTableCount = selectedShop.totalTableCount
        val usedTableCount = totalTableCount
    }

    // initialize kakao map api
    private fun initKakaoMapApi() {
        val mapView = MapView(context)
        val container =
            mPopupWindow.contentView.findViewById<RelativeLayout>(R.id.popup_window_market_location_map_container)
        container.addView(mapView)

        mapView.apply {
            setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude), true)
            setZoomLevel(1, true)
        }

        // add marker
        val markerPoint = MapPoint.mapPointWithGeoCoord(latitude, longitude)
        val marker = MapPOIItem()

        marker.apply {
            itemName = selectedShop.name
            tag = shopIdx
            mapPoint = markerPoint
            markerType = MapPOIItem.MarkerType.CustomImage
            customImageResourceId = R.drawable.ic_mini_marker
            isCustomImageAutoscale = true
            setCustomImageAnchor(0.5f, 1.0f)
        }

        mapView.addPOIItem(marker)
    }
}
