package com.keelim.domain.domain.url

import com.keelim.data.repository.url.UrlRepository

class UrlUseCase(
    private val urlRepository: UrlRepository
) {
    suspend fun share(url:String) {

    }
}