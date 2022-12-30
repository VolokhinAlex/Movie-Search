package com.example.java.android1.movie_search.view.theaters

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.CornerPathEffect
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.app.App
import com.example.java.android1.movie_search.databinding.FragmentMapBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polygon


class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    val geoPoints = ArrayList<GeoPoint>()
    val polygon = Polygon()

    private val mapView by lazy {
        binding.map
    }

    private val requestPermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach { permission ->
                if (permission.value) {
                    getCurrentLocation()
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val context = requireContext()
        Configuration.getInstance()
            .load(context, PreferenceManager.getDefaultSharedPreferences(context))
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility =
            View.GONE
        with(mapView) {
            clipToOutline = true
            setTileSource(TileSourceFactory.MAPNIK)
            val mapController = mapView.controller
            val startPosition = GeoPoint(37.000, 47.000)
            mapController.setZoom(9.5)
            mapController.animateTo(startPosition)
        }
        checkPermission(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION))
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    private fun checkPermission(
        permissions: Array<String>
    ) {
        context?.let { context ->
            when {
                permissions.all {
                    ContextCompat.checkSelfPermission(
                        context,
                        it
                    ) == PackageManager.PERMISSION_GRANTED
                } -> {
                    getCurrentLocation()
                }
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                    AlertDialog.Builder(context).setTitle("Доступ к геолокации")
                        .setMessage(
                            "Доступ к геолокации необходим для показа " +
                                    "текущих кинотеатров в вашем регионе"
                        ).setPositiveButton("Дать доступ") { dialog, _ ->
                            dialog.dismiss()
                            requestPermission()
                        }.setNegativeButton("Не давать доступ") { dialog, _ ->
                            dialog.dismiss()
                        }.create()
                }
                else -> {
                    requestPermission()
                }
            }
        }
    }

    private fun getCurrentLocation() {
        App.appInstance?.let { context ->
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val locationManager =
                    context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                val mapController = mapView.controller
                val marker = Marker(mapView)
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    val provider = locationManager.getProvider(LocationManager.GPS_PROVIDER)
                    provider?.let {
                        locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            60000L,
                            100.0f,
                        ) {
                            setCurrentPosition(mapController, it, marker)
                        }
                    }
                } else {
                    val location =
                        locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    if (location != null) {
                        setCurrentPosition(mapController, location, marker)
                    } else {
                        AlertDialog.Builder(context).setTitle("Геолокация выключена")
                            .setMessage(
                                "Для отображения вашей геолокации на карте, необходима включить GPS"
                            ).create()
                    }
                }
            } else {
                requestPermission()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun requestPermission() {
        requestPermissions.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION))
    }

    companion object {
        @JvmStatic
        fun newInstance() = MapFragment()
    }

    private fun setCurrentPosition(
        mapController: IMapController,
        location: Location,
        marker: Marker
    ) {
        mapController.setZoom(9.5)
        val geoPoint = GeoPoint(location.latitude, location.longitude)
        mapController.animateTo(geoPoint)
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
        marker.position = geoPoint
        marker.title = "Your location"
        marker.icon =
            context?.let { ContextCompat.getDrawable(it, R.drawable.ic_baseline_location_on_24) }
        mapView.overlays.add(marker)
        geoPoints.add(geoPoint.destinationPoint(10000.0, -90.0))
        geoPoints.add(geoPoint.destinationPoint(10000.0, 0.0))
        geoPoints.add(geoPoint.destinationPoint(10000.0, 90.0))
        geoPoints.add(geoPoint.destinationPoint(10000.0, 180.0))
        geoPoints.add(geoPoint.destinationPoint(10000.0, -90.0))
        val polygon = Polygon()
        polygon.points = geoPoints
        polygon.fillPaint.color = Color.parseColor("#1E0EFF1A")
        polygon.outlinePaint.pathEffect = CornerPathEffect(5000.0f)
        polygon.fillPaint.pathEffect = CornerPathEffect(5000.0f)
        mapView.overlays.add(polygon)
        mapView.invalidate()
    }

}