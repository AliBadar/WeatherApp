package com.ahoy.weatherapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahoy.weatherapp.data.models.forecast.ForeCastList
import com.ahoy.weatherapp.databinding.ListItemForecastBinding
import com.ahoy.weatherapp.databinding.ListItemSearchedCityTemperatureBinding
import com.ahoy.weatherapp.presentation.ui.main.Content
import com.ahoy.weatherapp.presentation.ui.main.ForeCastContent
import com.ahoy.weatherapp.presentation.ui.main.ForeCastWeatherUIState
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.text.DateFormat
import javax.inject.Inject

class ForeCastAdapter @Inject constructor(

) : RecyclerView.Adapter<ForeCastAdapter.UsersViewHolder>() {

    private var list: List<ForeCastWeatherUIState> = mutableListOf()

    class UsersViewHolder private constructor(private val binding: ListItemForecastBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            foreCastContent: ForeCastWeatherUIState,
        ) {

            foreCastContent?.let {foreCastWeather ->
                binding.textWeatherName.text = foreCastWeather.name
                binding.textTemperature.text = foreCastWeather.temp.toString()
                binding.textDateTime.text = foreCastWeather.date

            }
        }

        companion object {

            fun from(parent: ViewGroup): UsersViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemForecastBinding.inflate(layoutInflater, parent, false)
                return UsersViewHolder(binding)
            }

        }
    }

    fun setForeCastList(
        list: List<ForeCastWeatherUIState>
    ) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        return UsersViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        val item = list[position]
        item.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}