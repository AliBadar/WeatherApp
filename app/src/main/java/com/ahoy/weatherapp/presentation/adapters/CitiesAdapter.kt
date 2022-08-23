package com.ahoy.weatherapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahoy.weatherapp.R
import com.ahoy.weatherapp.databinding.ItemCityBinding
import com.ahoy.weatherapp.databinding.ListItemSearchedCityTemperatureBinding
import com.ahoy.weatherapp.presentation.ui.OnItemClickListener
import com.ahoy.weatherapp.presentation.ui.main.Content
import com.ahoy.weatherapp.presentation.ui.main.CurrentWeatherUIState
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.text.DateFormat
import javax.inject.Inject

class CitiesAdapter @Inject constructor(

) : RecyclerView.Adapter<CitiesAdapter.UsersViewHolder>() {

    private var list: List<CurrentWeatherUIState> = mutableListOf()

    private lateinit var onItemClickListener: OnItemClickListener

    class UsersViewHolder private constructor(private val binding: ItemCityBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            content: CurrentWeatherUIState,
            onItemClickListener: OnItemClickListener
        ) {

            content?.let {currentWeatherUIState ->
                binding.textCityName.text = currentWeatherUIState.name
                binding.textTemperature.text = currentWeatherUIState.temp.toString() + binding.textTemperature.context.getString(
                    R.string.degree)
                binding.txtHumidity.text = "H: ${currentWeatherUIState.humidity}${binding.txtHumidity.context.getString(
                    R.string.degree)}"
                binding.txtWind.text = "W: ${currentWeatherUIState.wind}${binding.txtWind.context.getString(R.string.degree)}"

            }

            itemView.setOnClickListener {
                onItemClickListener.onItemClick(content)
            }
        }

        companion object {

            fun from(parent: ViewGroup): UsersViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCityBinding.inflate(layoutInflater, parent, false)
                return UsersViewHolder(binding)
            }

        }
    }

    fun setOnItemClickListener(
        onItemClickListener: OnItemClickListener
    ){
        this.onItemClickListener = onItemClickListener
    }

    fun setCitiesList(
        list: List<CurrentWeatherUIState>
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
        item.let { holder.bind(it, onItemClickListener) }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}