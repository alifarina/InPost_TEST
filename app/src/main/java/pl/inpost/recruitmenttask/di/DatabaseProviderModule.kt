package pl.inpost.recruitmenttask.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.inpost.recruitmenttask.data.local.DatabaseHolder
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseProviderModule {

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

}