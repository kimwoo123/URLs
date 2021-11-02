package com.keelim.data.repository.url

import com.keelim.data.api.ApiRequestFactory
import com.keelim.data.model.open.Url
import com.keelim.di.IoDispatcher
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class UrlRepositoryImpl @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val apiRequestFactory: ApiRequestFactory,
) : UrlRepository {
    override suspend fun share(token: String): List<Url>  = withContext(dispatcher){
        TODO("Not yet implemented")

    }
}