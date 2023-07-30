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
import pl.inpost.recruitmenttask.network.ApiTypeAdapter
import pl.inpost.recruitmenttask.network.api.MockShipmentApi
import pl.inpost.recruitmenttask.network.api.ShipmentApi
import pl.inpost.recruitmenttask.usecases.ShipmentListUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun provideUseCase() = ShipmentListUseCase()

}