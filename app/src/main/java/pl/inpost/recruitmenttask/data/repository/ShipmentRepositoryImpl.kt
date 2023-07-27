package pl.inpost.recruitmenttask.data.repository

import androidx.lifecycle.LiveData
import pl.inpost.recruitmenttask.data.local.DatabaseHolder
import pl.inpost.recruitmenttask.data.local.entities.Shipment
import pl.inpost.recruitmenttask.domain.repository.ShipmentRepository
import pl.inpost.recruitmenttask.network.api.ShipmentApi
import pl.inpost.recruitmenttask.network.model.ShipmentNetwork
import pl.inpost.recruitmenttask.util.toEntity
import pl.inpost.recruitmenttask.util.toEntityUpdate
import pl.inpost.recruitmenttask.util.toShipmentNetworkModel
import javax.inject.Inject


class ShipmentRepositoryImpl @Inject constructor(
    private val databaseHolder: DatabaseHolder,
    private val shipmentApi: ShipmentApi
) : ShipmentRepository {

    override suspend fun getAllShipments(): List<ShipmentNetwork> {
        return if (databaseHolder.getShipmentsDao().checkShipmentsCount() > 0) {
            val shipmentList = databaseHolder.getShipmentsDao().getAllShipments()
                .map { it.toShipmentNetworkModel() }
            shipmentList
        } else {
            val listShipments = shipmentApi.getShipments() // based on API call
            databaseHolder.getShipmentsDao().insertAllShipments(listShipments.map { it.toEntity() })
            val shipmentList = databaseHolder.getShipmentsDao().getAllShipments()
                .map { it.toShipmentNetworkModel() }
            shipmentList
        }
    }

    override suspend fun refreshShipments(): List<ShipmentNetwork> {
        val listShipments = shipmentApi.getShipments()
        databaseHolder.getShipmentsDao().updateAllShipments(listShipments.map { it.toEntityUpdate() })
        return databaseHolder.getShipmentsDao().getAllShipments()
            .map { it.toShipmentNetworkModel() }
    }

    override suspend fun archiveItem(shipment_number: String) {
        databaseHolder.getShipmentsDao().setToArchive(shipment_number)
    }

    override fun observeShipmentsData(): LiveData<List<Shipment>> {
        return databaseHolder.getShipmentsDao().observeShipmentsData()
    }

}