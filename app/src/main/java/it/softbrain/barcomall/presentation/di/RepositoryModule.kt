package it.softbrain.barcomall.presentation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import it.softbrain.barcomall.data.repository.RepositoryIMPL
import it.softbrain.barcomall.data.repository.dataSource.RemoteDataSource
import it.softbrain.barcomall.domain.repository.Repository
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun providesLoginRepository(loginRemoteDataSource: RemoteDataSource):Repository
    {
        return RepositoryIMPL(loginRemoteDataSource)
    }

}