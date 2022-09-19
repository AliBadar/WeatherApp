package com.ahoy.domain

import javax.inject.Inject

class TestUseCase @Inject constructor(val repository: TestRepository) {

    operator fun invoke() = repository.getDescription()
}