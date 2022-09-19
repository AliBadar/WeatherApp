package com.ahoy.core.mapper

interface Mapper<E, D> {
    fun mapToEntity(type: D): E
}
