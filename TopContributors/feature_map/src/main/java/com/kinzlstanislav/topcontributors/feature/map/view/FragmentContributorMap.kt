package com.kinzlstanislav.topcontributors.feature.map.view

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.kinzlstanislav.topcontributors.architecture.core.extension.bindArgument
import com.kinzlstanislav.topcontributors.architecture.core.model.User
import com.kinzlstanislav.topcontributors.base.Constants.EXTRAS_LOCATION
import com.kinzlstanislav.topcontributors.base.Constants.EXTRAS_USER
import com.kinzlstanislav.topcontributors.base.view.BaseFragment
import com.kinzlstanislav.topcontributors.feature.map.R

class FragmentContributorMap : BaseFragment(), OnMapReadyCallback {

    override val layoutResourceId = R.layout.fragment_contributor_map

    private val user: User by bindArgument(EXTRAS_USER)
    private val location: LatLng by bindArgument(EXTRAS_LOCATION)

    override fun onMapReady(googleMap: GoogleMap?) {
        val toUserLocation = CameraUpdateFactory.newLatLng(location)
        val zoom = CameraUpdateFactory.zoomTo(15f)

        googleMap?.apply {
            moveCamera(toUserLocation)
            animateCamera(zoom)
        }
    }

    override fun onFragmentCreated() {
        showToast(location.latitude.toString())
    }

}