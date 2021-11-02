package com.keelim.domain.domain.url

import com.keelim.data.model.CallResult
import com.keelim.data.model.open.Url
import com.keelim.data.repository.url.UrlRepository

class UrlUseCase(
    private val urlRepository: UrlRepository
) {
    suspend fun share(token:String): List<Url> {
        return urlRepository.share(token)
    }

    suspend fun inject(token:String, url:String): CallResult{
        return urlRepository.inject(token, url)
    }
}