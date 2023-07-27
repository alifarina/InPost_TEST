package pl.inpost.recruitmenttask.usecases

import android.util.Log
import pl.inpost.recruitmenttask.domain.usecases.ShipmentUseCase
import pl.inpost.recruitmenttask.network.model.ShipmentNetwork
import pl.inpost.recruitmenttask.network.model.ShipmentStatus

class ShipmentListUseCase : ShipmentUseCase {

    override fun sortShipments(listOfShipments: List<ShipmentNetwork>): List<ShipmentNetwork> {
        val comparator =
            compareBy<ShipmentNetwork> { ShipmentStatus.valueOf(it.status) }
                .thenBy { it.pickUpDate }
                .thenBy { it.expiryDate }
                .thenBy { it.storedDate }
                .thenBy { it.number }

        return listOfShipments.sortedWith(comparator)
    }

    override fun groupShipments(shipmentList: List<ShipmentNetwork>): HashMap<String, List<ShipmentNetwork>> {
        val groupedResult = shipmentList.groupBy { it.operations.highlight }
        Log.d("shipments", shipmentList.size.toString())
        val mapTitleData = HashMap<String, List<ShipmentNetwork>>()
        groupedResult.forEach { (key, value) ->
            if (key) {
                mapTitleData["Gotowe do odbioru"] = value
            } else {
                mapTitleData["Pozostałe"] = value
            }
        }
        return mapTitleData
    }


}