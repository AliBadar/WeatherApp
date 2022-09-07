package com.ahoy.weatherapp.data.mapper

interface Mapper<E, D> {
    fun mapToEntity(type: D): E
}
