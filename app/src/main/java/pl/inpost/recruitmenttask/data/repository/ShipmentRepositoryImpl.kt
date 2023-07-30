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

/**
 * Repository class to provide data from API or database
 */
class ShipmentRepositoryImpl @Inject constructor(
    private val databaseHolder: DatabaseHolder,
    private val shipmentApi: ShipmentApi
) : ShipmentRepository {

    /**
     * called first time everytime on opening the app
     */
    override suspend fun getAllShipments(): List<ShipmentNetwork> {
        if (databaseHolder.getShipmentsDao().checkShipmentsCount() == 0) {
            // no data in database, perform insert
            val listShipments = shipmentApi.getShipments() // based on API call
            if (listShipments.isNotEmpty()) {
                databaseHolder.getShipmentsDao()
                    .insertAllShipments(listShipments.map { it.toEntity() })
            }
        }
        return databaseHolder.getShipmentsDao().getAllShipments()
            .map { it.toShipmentNetworkModel() }
    }

    /**
     * called on pull to refresh
     */
    override suspend fun refreshShipments(): List<ShipmentNetwork> {
        val listShipments = shipmentApi.getShipments()
        if(listShipments.isNotEmpty()){
            if (databaseHolder.getShipmentsDao().checkShipmentsCount() == 0) {
                // insert in case of table is empty
                databaseHolder.getShipmentsDao()
                    .insertAllShipments(listShipments.map { it.toEntity() })
            }else{
                // update in case of table is not empty
                databaseHolder.getShipmentsDao()
                    .updateAllShipments(listShipments.map { it.toEntityUpdate() })
            }
        }
        // return data from database
        return databaseHolder.getShipmentsDao().getAllShipments()
            .map { it.toShipmentNetworkModel() }
    }

    /**
     * called on archive item click
     */
    override suspend fun archiveItem(shipment_number: String) {
        databaseHolder.getShipmentsDao().setToArchive(shipment_number)
    }

    /**
     * observer for live data changes from DB
     */
    override fun observeShipmentsData(): LiveData<List<Shipment>> {
        return databaseHolder.getShipmentsDao().observeShipmentsData()
    }

}