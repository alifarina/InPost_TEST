package pl.inpost.recruitmenttask.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.inpost.recruitmenttask.data.local.DatabaseHolder
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DaoProviderModule {

    @Singleton
    @Provides
    fun provideShipmentsDao(db: DatabaseHolder) = db.getShipmentsDao()

}