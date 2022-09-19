package com.ahoy.data

import com.ahoy.domain.TestRepository

class TestRepositoryImp: TestRepository {
    override fun getDescription(): String {
        return "Dynamic Feature"
    }
}