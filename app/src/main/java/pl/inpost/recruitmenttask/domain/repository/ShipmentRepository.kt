package pl.inpost.recruitmenttask.domain.repository

import androidx.lifecycle.LiveData
import pl.inpost.recruitmenttask.data.local.entities.Shipment
import pl.inpost.recruitmenttask.network.model.ShipmentNetwork

/**
 * contains functions that needs to be defined
 */
interface ShipmentRepository {

    suspend fun getAllShipments(): List<ShipmentNetwork>

    suspend fun refreshShipments(): List<ShipmentNetwork>

    suspend fun archiveItem(shipment_number : String)

    fun observeShipmentsData() : LiveData<List<Shipment>>
}