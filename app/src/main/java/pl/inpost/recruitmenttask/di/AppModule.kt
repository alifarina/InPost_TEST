package pl.inpost.recruitmenttask.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.inpost.recruitmenttask.data.local.DatabaseHolder
import pl.inpost.recruitmenttask.data.repository.ShipmentRepositoryImpl
import pl.inpost.recruitmenttask.domain.repository.ShipmentRepository
import pl.inpost.recruitmenttask.network.ApiTypeAdapter
import pl.inpost.recruitmenttask.network.api.MockShipmentApi
import pl.inpost.recruitmenttask.network.api.ShipmentApi
import pl.inpost.recruitmenttask.usecases.ShipmentListUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideAppRepository(databaseHolder: DatabaseHolder, shipmentApi: ShipmentApi) =
        ShipmentRepositoryImpl(databaseHolder = databaseHolder, shipmentApi = shipmentApi)

    @Singleton
    @Provides
    fun provideYourDatabase(@ApplicationContext app: Context) =
        Room.databaseBuilder(
            app,
            DatabaseHolder::class.java,
            "db_shipments"
        )
            .fallbackToDestructiveMigration()
            .build() // The reason we can construct a database for the repo

    @Singleton
    @Provides
    fun provideShipmentsDao(db: DatabaseHolder) = db.getShipmentsDao()

    @Singleton
    @Provides
    fun shipmentApi(
        @ApplicationContext context: Context,
        apiTypeAdapter: ApiTypeAdapter
    ): ShipmentApi = MockShipmentApi(context, apiTypeAdapter)

    @Singleton
    @Provides
    fun provideUseCase() = ShipmentListUseCase()

}