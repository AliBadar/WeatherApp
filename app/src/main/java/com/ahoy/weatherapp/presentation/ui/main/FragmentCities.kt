package com.ahoy.weatherapp.presentation.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahoy.weatherapp.databinding.FragmentCityBinding
import com.ahoy.weatherapp.databinding.FragmentWeatherBinding
import com.ahoy.weatherapp.presentation.adapters.CitiesAdapter
import com.ahoy.weatherapp.presentation.ui.OnItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FragmentCities: Fragment(), OnItemClickListener {

    private val citiesViewModel: CitiesViewModel by viewModels()
    private lateinit var mBinding: FragmentCityBinding


    @Inject
    lateinit var citiesAdapter: CitiesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentCityBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.shimmerViewContainer.startShimmer();
        setUpCitiesRecyclerView()
        initObservations()
        initListeners()
    }

    private fun setUpCitiesRecyclerView(){
        mBinding.recyclerViewSearchedCityTemperature.also {
//            it.layoutManager = LinearLayoutManager(requireContext(), LinearLayout.HORIZONTAL, false)
//            it.addItemDecoration(
//                DividerItemDecoration(context, LinearLayout.VERTICAL)
//            )
//            it.isNestedScrollingEnabled = false
            it.adapter = citiesAdapter
            citiesAdapter.setOnItemClickListener(this)
        }
    }

    private fun initObservations(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                citiesViewModel.favCitiesData.collect { favCitiesState: FavCitiesMainUiState ->
                    updateFavCites(favCitiesState)
                }
            }
        }
    }

    private fun initListeners(){
        mBinding.inputFindCityWeather.setOnEditorActionListener { view, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                findNavController().previousBackStackEntry?.savedStateHandle?.set("key", (view as EditText).text.toString())
                findNavController().popBackStack()
            }
            false
        }

        mBinding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun updateFavCites(favCitiesState: FavCitiesMainUiState){
        when(favCitiesState){
            is LoadingFavState -> {

            }
            is FavContent -> {
                citiesAdapter.setCitiesList(favCitiesState.favCitiesUIState)
                mBinding.shimmerViewContainer.visibility = View.GONE
            }
        }
    }

    override fun onItemClick(currentWeatherUIState: CurrentWeatherUIState) {
        findNavController().previousBackStackEntry?.savedStateHandle?.set("key", currentWeatherUIState.name)
        findNavController().popBackStack()
    }
}