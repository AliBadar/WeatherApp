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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahoy.weatherapp.R
import com.ahoy.weatherapp.data.models.forecast.ForeCastList
import com.ahoy.weatherapp.databinding.FragmentCityBinding
import com.ahoy.weatherapp.databinding.FragmentMainBinding
import com.ahoy.weatherapp.databinding.FragmentWeatherBinding
import com.ahoy.weatherapp.presentation.adapters.CitiesAdapter
import com.ahoy.weatherapp.presentation.adapters.ForeCastAdapter
import com.ahoy.weatherapp.presentation.ui.OnItemClickListener
import com.ahoy.weatherapp.presentation.ui.splash.SplashFragmentDirections
import com.ahoy.weatherapp.utils.PermissionsUtils
import com.ahoy.weatherapp.utils.showSnack
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class WeatherFragment : Fragment(), OnItemClickListener {

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var mBinding: FragmentWeatherBinding

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
        mBinding = FragmentWeatherBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getCurrentLocation()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.shimmerViewContainer.startShimmer();
        setUpForeCastRecyclerView()
        initObservations()
        initListeners()
    }

    private fun initListeners(){

//        mBinding.swipeRefresh.setOnRefreshListener {
//            mBinding.swipeRefresh.isRefreshing = false
//            getCurrentLocation()
//            mBinding.inputFindCityWeather.setText("")
//        }

        mBinding.btnSearch.setOnClickListener {
            findNavController().navigate(WeatherFragmentDirections.actionMainFragmentToFragmentCities())
        }

    }


    private fun initObservations(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.currentWeatherData.collect { mainUIState: MainUiState ->
                    updateUI(mainUIState)
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

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("key")?.observe(viewLifecycleOwner){result ->
            if (result.isNotEmpty()){
                filterData(result)
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

    private fun setUpForeCastRecyclerView(){
        mBinding.recyclerViewForecast.also {
//            it.layoutManager = LinearLayoutManager(requireContext())
//            it.addItemDecoration(
//                DividerItemDecoration(context, LinearLayout.VERTICAL)
//            )
//            it.isNestedScrollingEnabled = false
            it.adapter = foreCastAdapter
        }
    }

    private fun updateUI(mainUIState: MainUiState){
        when(mainUIState){
            is LoadingState -> {
                showLoader()
            }
            is Content -> {
                setData(mainUIState.currentWeatherUIState)
            }
            is ErrorState -> {
                view?.showSnack(mainUIState.message)
            }
        }
    }

    private fun updateForeCastUi(foreCastMainUIState: ForeCastMainUiState){
        when(foreCastMainUIState){
            is ForeCastLoadingState -> {
            }
            is ForeCastContent -> {
                setForeCastData(foreCastMainUIState.foreCastListData)
                hideLoader()
            }
            is ForeCastErrorState -> {
                view?.showSnack(foreCastMainUIState.message)
            }
        }
    }

    private fun setForeCastData(foreCastList: List<ForeCastWeatherUIState>){
        foreCastAdapter.setForeCastList(foreCastList)
    }

    private fun setData(currentWeatherUIState: CurrentWeatherUIState){
        mBinding.textTemperature.text = currentWeatherUIState.temp.toString()+getString(
                R.string.degree)

        mBinding.textCityName.text = currentWeatherUIState.name
        mBinding.textHumidity.text = "H:${currentWeatherUIState.humidity}${getString(
            R.string.degree)}"
        mBinding.textWind.text = "W:${currentWeatherUIState.wind}${getString(
            R.string.degree)}"
//        changeBackground(currentWeatherUIState.iconCode)
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

    private fun showLoader(){
        mBinding.shimmerViewContainer.visibility = View.VISIBLE
//        if(mBinding.progressPhotos.visibility != View.VISIBLE){
//            mBinding.progressPhotos.visibility = View.VISIBLE
//        }
    }

    private fun hideLoader(){
        mBinding.shimmerViewContainer.visibility = View.GONE
//        if(mBinding.progressPhotos.visibility == View.VISIBLE){
//            mBinding.progressPhotos.visibility = View.GONE
//        }
    }

    override fun onItemClick(currentWeatherUIState: CurrentWeatherUIState) {
        filterData(currentWeatherUIState.name)
    }

    private fun filterData(searchQuery: String){
        mainViewModel.getCurrentWeather(q = searchQuery, saveIntoDB = true)
        mainViewModel.getForeCast(q = searchQuery)
    }
}