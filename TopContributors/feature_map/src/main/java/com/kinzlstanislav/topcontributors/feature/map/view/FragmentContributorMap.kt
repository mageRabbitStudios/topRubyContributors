package com.kinzlstanislav.topcontributors.feature.map.view

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.kinzlstanislav.topcontributors.architecture.repository.model.User
import com.kinzlstanislav.topcontributors.base.Constants.EXTRAS_LOCATION
import com.kinzlstanislav.topcontributors.base.Constants.EXTRAS_USER
import com.kinzlstanislav.topcontributors.base.extensions.bindArgument
import com.kinzlstanislav.topcontributors.base.extensions.showToast
import com.kinzlstanislav.topcontributors.base.view.BaseFragment
import com.kinzlstanislav.topcontributors.feature.map.R

class FragmentContributorMap : BaseFragment(), OnMapReadyCallback {

    private companion object {
        const val CAMERA_ZOOM = 15f
    }

    override val layoutResourceId = R.layout.fragment_contributor_map

    private val user: User by bindArgument(EXTRAS_USER)
    private val location: LatLng by bindArgument(EXTRAS_LOCATION)

    override fun onFragmentCreated() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        val toUserLocation = CameraUpdateFactory.newLatLng(location)
        val zoom = CameraUpdateFactory.zoomTo(CAMERA_ZOOM)

        googleMap?.apply {
            moveCamera(toUserLocation)
            animateCamera(zoom)
            addMarker(MarkerOptions()
                .position(location)
                .title(user.name)
            )
        } ?: run {
            showToast("Google Map is null! :-( Oh what are we if the world falls.")
        }
    }
}