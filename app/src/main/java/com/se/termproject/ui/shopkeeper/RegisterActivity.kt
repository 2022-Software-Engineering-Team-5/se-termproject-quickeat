package com.se.termproject.ui.shopkeeper

import android.Manifest
import android.content.ContentResolver
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
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
    }

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private lateinit var manager: LocationManager
    private lateinit var gpsListener: GPSListener

    // Kakao Map API
    private lateinit var mapView: MapView
    private lateinit var coverImgUrl: Uri

    // firebase
    private lateinit var user: FirebaseUser
    private lateinit var mDatabase: FirebaseDatabase
    private lateinit var mShopsReference: DatabaseReference
    private lateinit var mStorageReference: StorageReference

    // storage
    private lateinit var activityResult: ActivityResultLauncher<Intent>

    override fun initAfterBinding() {
        initReference()

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            0
        )

        activityResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == RESULT_OK && it.data != null) {
                coverImgUrl = it.data!!.data!!
                binding.shopkeeperRegisterCoverImgIv.setImageURI(coverImgUrl)
            }
        }

        // ????????? ?????? ????????????
        user = Firebase.auth.currentUser!!
        saveUserId(user.uid)
        USER_ID = getUserId()!!
//        mShopsReference.setValue(USER_ID)

        initClickListener()
    }

    private fun initReference() {
        mDatabase = FirebaseDatabase.getInstance()
        mShopsReference = mDatabase.getReference("shops")
        mStorageReference = FirebaseStorage.getInstance().reference
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
        marker.itemName = "?????? ????????? ???????????????."
        marker.tag = 1
        marker.mapPoint = markerPoint
        marker.markerType = MapPOIItem.MarkerType.CustomImage
        marker.customImageResourceId = R.drawable.ic_mini_marker
        marker.isCustomImageAutoscale = true
        marker.setCustomImageAnchor(0.5f, 1.0f)
        mapView.addPOIItem(marker)
    }

    private fun initClickListener() {

        // ?????? ???????????? ?????? ?????? ???
        binding.shopkeeperRegisterGetLocationBtn.setOnClickListener {
            startLocationService()
        }

        // ????????? ?????????
        binding.shopkeeperRegisterCoverImgIv.setOnClickListener {
            val galleryIntent = Intent()
            galleryIntent.action = Intent.ACTION_GET_CONTENT
            galleryIntent.type = "image/"
            activityResult.launch(galleryIntent)
        }

        // ????????? ????????? ?????? ?????? ???
        binding.shopkeeperRegisterCoverImgBtn.setOnClickListener {
            // ????????? ???????????? ?????????
            uploadImg(coverImgUrl)
        }

        // check
        binding.shopkeeperRegisterCheckBtn.setOnClickListener {
            val shopName = binding.shopkeeperRegisterShopNameEt.text.toString()
            val shopDesc = binding.shopkeeperRegisterShopDescriptionEt.text.toString()
            var totalTableCount = 0

            if (binding.shopkeeperRegisterTotalTableCountEt.text.toString() == "") {
                Toast.makeText(this, "????????? ???????????? ???????????????.", Toast.LENGTH_SHORT).show()
            } else {
                totalTableCount =
                    Integer.parseInt(binding.shopkeeperRegisterTotalTableCountEt.text.toString())

                val shop = Shop(
                    shopName,
                    shopDesc,
                    latitude,
                    longitude,
                    totalTableCount,
                    0,
                    coverImgUrl.toString()
                )

                mShopsReference.child(USER_ID).setValue(shop)
                    .addOnSuccessListener {
                        Toast.makeText(this, "?????? ??????", Toast.LENGTH_SHORT).show()
                        startNextActivity(MainActivity::class.java)
                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "?????? ??????", Toast.LENGTH_SHORT).show()
                    }
            }
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

    // upload the image to firebase
    private fun uploadImg(uri: Uri) {
        val mFileReference: StorageReference = mStorageReference.child(USER_ID)
            .child("${System.currentTimeMillis()}.${getFileExtension(uri)}")

        mFileReference.putFile(uri).addOnSuccessListener {

            mFileReference.downloadUrl.addOnSuccessListener {
                coverImgUrl = it
                mShopsReference.child(USER_ID).child("coverImg").setValue(coverImgUrl.toString())
                Toast.makeText(this, "????????? ??????", Toast.LENGTH_SHORT).show()
                binding.shopkeeperRegisterCoverImgIv.setImageURI(uri)
            }
        }.addOnFailureListener {
            Toast.makeText(this, "????????? ??????", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getFileExtension(uri: Uri): String {
        val contentResolver: ContentResolver = contentResolver
        val mime: MimeTypeMap = MimeTypeMap.getSingleton()

        return mime.getExtensionFromMimeType(contentResolver.getType(uri))!!
    }
}
