package pl.inpost.recruitmenttask.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import pl.inpost.recruitmenttask.data.local.entities.Shipment
import pl.inpost.recruitmenttask.data.local.entities.ShipmentUpdate
import pl.inpost.recruitmenttask.network.model.ShipmentNetwork

/**
 * DAO for accessing database with queries
 */
@Dao
interface ShipmentsDao {

    @Insert(onConflict = REPLACE)
    fun insertAllShipments(listOfShipments : List<Shipment>)

    @Update()
    fun updateAllShipments(listOfShipments: List<ShipmentUpdate>)

    @Query("select * from shipment  where is_archived = 0")
    fun getAllShipments() : List<Shipment>

    @Query("select * from shipment where is_archived = 0")
    fun observeShipmentsData() : LiveData<List<Shipment>>

    @Query("select count(s.number) from shipment as s")
    fun checkShipmentsCount() : Int

    @Query("update shipment set is_archived = 1 where number = :shipment_number")
    fun setToArchive(shipment_number : String)
}