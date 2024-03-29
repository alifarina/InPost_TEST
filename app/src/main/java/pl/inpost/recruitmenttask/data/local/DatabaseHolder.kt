package pl.inpost.recruitmenttask.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.inpost.recruitmenttask.data.local.dao.ShipmentsDao
import pl.inpost.recruitmenttask.data.local.entities.Shipment
import pl.inpost.recruitmenttask.data.local.entities.ShipmentUpdate

/**
 * Database class
 */
@Database(
    version = 1,
    entities = [Shipment::class, ShipmentUpdate::class]
)
abstract class DatabaseHolder : RoomDatabase() {

    abstract fun getShipmentsDao(): ShipmentsDao
}