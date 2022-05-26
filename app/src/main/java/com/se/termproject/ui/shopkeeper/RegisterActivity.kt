package com.se.termproject.ui.shopkeeper

import android.Manifest
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.se.termproject.R
import com.se.termproject.base.kotlin.BaseActivity
import com.se.termproject.data.Shop
import com.se.termproject.databinding.ActivityShopkeeperRegisterBinding
import com.se.termproject.util.getUserId
import com.se.termproject.util.saveUserId
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class RegisterActivity :
    BaseActivity<ActivityShopkeeperRegisterBinding>(ActivityShopkeeperRegisterBinding::inflate) {
    companion object {
        private const val TAG = "ACT/REGISTER"
        private lateinit var USER_ID: String

//        private val GPS_ENABLE_REQUEST_CODE: Int = 2001
//        private val PERMISSIONS_REQUEST_CODE: Int = 100
//        private var REQUIRED_PERMISSIONS = { Manifest.permission.ACCESS_FINE_LOCATION }
    }

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private lateinit var manager: LocationManager
    private lateinit var gpsListener: GPSListener

    // Kakao Map API
    private lateinit var mapView: MapView

    // Firebase
    private lateinit var user: FirebaseUser
    private lateinit var mDatabase: FirebaseDatabase
    private lateinit var mShopsReference: DatabaseReference

    override fun initAfterBinding() {
        initReference()

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            0
        )

        // 사용자 정보 받아오기
        user = Firebase.auth.currentUser!!
        saveUserId(user.uid)
        USER_ID = getUserId()!!
        mShopsReference.setValue(USER_ID)

        initClickListener()
    }

    private fun initReference() {
        mDatabase = FirebaseDatabase.getInstance()
        mShopsReference = mDatabase.getReference("shops")
    }

    private fun startLocationService() {
        manager = getSystemService(LOCATION_SERVICE) as LocationManager

        try {
            gpsListener = GPSListener()
            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f, gpsListener)
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    private fun initKakaoMapApi() {
        Log.d(TAG, "latitude: $latitude")
        Log.d(TAG, "longitude: $longitude")

        mapView = MapView(this)
        binding.shopkeeperRegisterShopLocationContainer.addView(mapView)

        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude), true)
        mapView.setZoomLevel(1, true)

        // marker
        val markerPoint = MapPoint.mapPointWithGeoCoord(latitude, longitude)
        val marker = MapPOIItem()
        marker.itemName = "가게 위치를 확인하세요."
        marker.tag = 1
        marker.mapPoint = markerPoint
        marker.markerType = MapPOIItem.MarkerType.CustomImage
        marker.customImageResourceId = R.drawable.ic_mini_marker
        marker.isCustomImageAutoscale = true
        marker.setCustomImageAnchor(0.5f, 1.0f)
        mapView.addPOIItem(marker)
    }

    private fun initClickListener() {

        // 위치 받아오기 버튼 클릭 시
        binding.shopkeeperRegisterGetLocationBtn.setOnClickListener {
            startLocationService()
        }

        // check
        binding.shopkeeperRegisterCheckBtn.setOnClickListener {
            val shopName = binding.shopkeeperRegisterShopNameEt.text.toString()
            val shopDesc = binding.shopkeeperRegisterShopDescriptionEt.text.toString()
            val totalTableCount =
                Integer.parseInt(binding.shopkeeperRegisterTotalTableCountEt.text.toString())
//            val tableTypeCount =
//                Integer.parseInt(binding.shopkeeperRegisterTableTypeEt.text.toString())

            val shop = Shop(shopName, shopDesc, latitude, longitude, totalTableCount, 0)

            mShopsReference.child(USER_ID).setValue(shop)
                .addOnSuccessListener {
                    Toast.makeText(this, "등록 완료", Toast.LENGTH_SHORT).show()
                    startNextActivity(MainActivity::class.java)
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "등록 실패", Toast.LENGTH_SHORT).show()
                }

//            mShopsReference.child(USER_ID).child("name").setValue(shopName)
//            mShopsReference.child(USER_ID).child("description").setValue(shopDesc)
//            mShopsReference.child(USER_ID).child("total_table_count").setValue(totalTableCount)
//            mShopsReference.child(USER_ID).child("available_table_count").setValue(0)
//            mShopsReference.child(USER_ID).child("latitude").setValue(latitude)
//            mShopsReference.child(USER_ID).child("longitude").setValue(longitude)
        }
    }

    inner class GPSListener : LocationListener {

        override fun onLocationChanged(location: Location) {
            Log.d(TAG, "onLocationChanged")

            latitude = location.latitude
            longitude = location.longitude

            initKakaoMapApi()
            manager.removeUpdates(gpsListener)
        }
    }
}
