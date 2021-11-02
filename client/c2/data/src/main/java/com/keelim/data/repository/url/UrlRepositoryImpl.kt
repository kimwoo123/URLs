package com.keelim.data.repository.url

import com.keelim.data.api.ApiRequestFactory
import com.keelim.data.model.CallResult
import com.keelim.data.model.notification.NotificationType
import com.keelim.data.model.open.Url
import com.keelim.di.IoDispatcher
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class UrlRepositoryImpl @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val apiRequestFactory: ApiRequestFactory,
) : UrlRepository {
    override suspend fun share(token: String): List<Url>  = withContext(dispatcher) {
        val response = apiRequestFactory.retrofit.share(token)
        if (response.isSuccessful) {
            response.body()?.mapIndexed{ index, url ->

            }
            return@withContext response.body() ?: emptyList()
        } else {
            return@withContext listOf()
        }
    }

    override suspend fun inject(token: String, url: String): CallResult = withContext(dispatcher) {
        return@withContext CallResult.Success
    }
}