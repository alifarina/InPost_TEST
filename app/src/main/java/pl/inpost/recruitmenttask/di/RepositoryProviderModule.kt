package pl.inpost.recruitmenttask.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.inpost.recruitmenttask.data.local.DatabaseHolder
import pl.inpost.recruitmenttask.data.repository.ShipmentRepositoryImpl
import pl.inpost.recruitmenttask.network.api.ShipmentApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryProviderModule {

    @Provides
    @Singleton
    fun provideAppRepository(databaseHolder: DatabaseHolder, shipmentApi: ShipmentApi) =
        ShipmentRepositoryImpl(databaseHolder = databaseHolder, shipmentApi = shipmentApi)


}