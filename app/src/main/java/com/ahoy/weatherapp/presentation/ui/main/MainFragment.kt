package com.ahoy.weatherapp.presentation.ui.main

import android.Manifest
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahoy.weatherapp.R
import com.ahoy.weatherapp.data.models.forecast.ForeCastList
import com.ahoy.weatherapp.databinding.FragmentMainBinding
import com.ahoy.weatherapp.presentation.adapters.CitiesAdapter
import com.ahoy.weatherapp.presentation.adapters.ForeCastAdapter
import com.ahoy.weatherapp.presentation.ui.OnItemClickListener
import com.ahoy.weatherapp.utils.PermissionsUtils
import com.ahoy.weatherapp.utils.showSnack
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainFragment : Fragment(), OnItemClickListener {

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var mBinding: FragmentMainBinding

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var mContentList : List<Content>

    @Inject
    lateinit var citiesAdapter: CitiesAdapter

    @Inject
    lateinit var foreCastAdapter: ForeCastAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getCurrentLocation()
        setUpCitiesRecyclerView()
        setUpForeCastRecyclerView()
        initObservations()
        initListeners()
    }

    private fun initListeners(){
        mBinding.inputFindCityWeather.setOnEditorActionListener { view, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                filterData((view as EditText).text.toString())
            }
            false
        }

        mBinding.swipeRefresh.setOnRefreshListener {
            mBinding.swipeRefresh.isRefreshing = false
            getCurrentLocation()
            mBinding.inputFindCityWeather.setText("")
        }
    }


    private fun initObservations(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.currentWeatherData.collect { mainUIState: MainUiState ->
                    updateUI(mainUIState)
                    mainViewModel.getFavCities()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.favCitiesData.collect { favCitiesState: FavCitiesMainUiState ->
                    updateFavCites(favCitiesState)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.foreCastData.collect { foreCastMainUIState: ForeCastMainUiState ->
                    updateForeCastUi(foreCastMainUIState)
                }
            }
        }
    }

    private fun updateFavCites(favCitiesState: FavCitiesMainUiState){
        when(favCitiesState){
            is LoadingFavState -> {

            }
            is FavContent -> {
                citiesAdapter.setCitiesList(favCitiesState.favCitiesUIState)
            }
        }
    }

    private fun setUpCitiesRecyclerView(){
        mBinding.recyclerViewSearchedCityTemperature.also {
            it.layoutManager = LinearLayoutManager(requireContext(), LinearLayout.HORIZONTAL, false)
            it.addItemDecoration(
                DividerItemDecoration(context, LinearLayout.VERTICAL)
            )
            it.isNestedScrollingEnabled = false
            it.adapter = citiesAdapter
            citiesAdapter.setOnItemClickListener(this)
        }
    }

    private fun setUpForeCastRecyclerView(){
        mBinding.recyclerViewForecast.also {
            it.layoutManager = LinearLayoutManager(requireContext())
            it.addItemDecoration(
                DividerItemDecoration(context, LinearLayout.VERTICAL)
            )
            it.isNestedScrollingEnabled = false
            it.adapter = foreCastAdapter
        }
    }

    private fun updateUI(mainUIState: MainUiState){
        when(mainUIState){
            is LoadingState -> {
                showLoader()
            }
            is Content -> {
                hideLoader()
                setData(mainUIState.currentWeatherUIState)
            }
            is ErrorState -> {
                hideLoader()
                view?.showSnack(mainUIState.message)
            }
        }
    }

    private fun updateForeCastUi(foreCastMainUIState: ForeCastMainUiState){
        when(foreCastMainUIState){
            is ForeCastLoadingState -> {
                showLoader()
            }
            is ForeCastContent -> {
                hideLoader()
                setForeCastData(foreCastMainUIState.foreCastListData)
            }
            is ForeCastErrorState -> {
                hideLoader()
                view?.showSnack(foreCastMainUIState.message)
            }
        }
    }

    private fun setForeCastData(foreCastList: List<ForeCastWeatherUIState>){
        foreCastAdapter.setForeCastList(foreCastList)
    }

    private fun setData(currentWeatherUIState: CurrentWeatherUIState){
        mBinding.textTemperature.text = currentWeatherUIState.temp.toString()
        mBinding.textTodaysDate.text = currentWeatherUIState.date
        mBinding.textCityName.text = currentWeatherUIState.name
        mBinding.textHumidity.text = "Humidity: ${currentWeatherUIState.humidity}%"
        mBinding.textWind.text = "Wind: ${currentWeatherUIState.wind} km/h"
        changeBackground(currentWeatherUIState.iconCode)
    }

    private fun obtainLocation() {
        // Location Provider Client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        // get the last location
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                // get the latitude and longitude
                // and create the http URL
                location?.let { loc ->
                    mainViewModel.getCurrentWeather(lat = loc.latitude.toString(),lon =  loc.longitude.toString())
                    mainViewModel.getForeCast(lat = loc.latitude.toString(),lon =  loc.longitude.toString())
                }

            }
            .addOnCanceledListener {
                Log.e("error", "error")
            }
    }

   private fun getCurrentLocation(){
       when {
           PermissionsUtils.isAccessFineLocationGranted(requireContext()) -> {
               when {
                   PermissionsUtils.isLocationEnabled(requireContext()) -> {
                       obtainLocation()
                   }
                   else -> {
                       PermissionsUtils.showGPSNotEnabledDialog(requireContext())
                   }
               }
           }
           else -> {
               requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
           }
       }
   }

    private val requestPermissionLauncher = registerForActivityResult(
        RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission is granted. Continue the action or workflow in your
            // app.
            obtainLocation()
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.location_permission_not_granted),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun changeBackground(iconCode: String?) {
        when (iconCode) {
            "01d", "02d", "03d" -> mBinding.imageWeatherHumanReaction.setImageResource(R.drawable.sunny_day)
            "04d", "09d", "10d", "11d" -> mBinding.imageWeatherHumanReaction.setImageResource(R.drawable.raining)
            "13d", "50d" -> mBinding.imageWeatherHumanReaction.setImageResource(R.drawable.snowfalling)
        }
    }

    private fun showLoader(){
        if(mBinding.progressPhotos.visibility != View.VISIBLE){
            mBinding.progressPhotos.visibility = View.VISIBLE
        }
    }

    private fun hideLoader(){
        if(mBinding.progressPhotos.visibility == View.VISIBLE){
            mBinding.progressPhotos.visibility = View.GONE
        }
    }

    override fun onItemClick(currentWeatherUIState: CurrentWeatherUIState) {
        filterData(currentWeatherUIState.name)
    }

    private fun filterData(searchQuery: String){
        mainViewModel.getCurrentWeather(q = searchQuery, saveIntoDB = true)
        mainViewModel.getForeCast(q = searchQuery)
    }
}