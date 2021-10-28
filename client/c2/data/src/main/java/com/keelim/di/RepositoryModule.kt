package com.keelim.di

import com.keelim.data.api.ApiRequestFactory
import com.keelim.data.repository.NotificationRepository
import com.keelim.data.repository.NotificationRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

  @Provides
  @Singleton
  fun provideNotificationRepository(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    apiRequestFactory: ApiRequestFactory,
  ): NotificationRepository {
    return NotificationRepositoryImpl(
      dispatcher,
      apiRequestFactory
    )
  }
}
