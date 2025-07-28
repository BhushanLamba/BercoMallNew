package it.softbrain.barcomall.presentation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import it.softbrain.barcomall.data.api.WebService
import it.softbrain.barcomall.data.repository.dataSource.RemoteDataSource
import it.softbrain.barcomall.data.repository.dataSourceIMPL.RemoteDataSourceIMPL
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class RemoteDataModule {


    @Singleton
    @Provides
    fun providesLoginRemoteDataSource(webService: WebService): RemoteDataSource {
        return RemoteDataSourceIMPL(webService)
    }

}