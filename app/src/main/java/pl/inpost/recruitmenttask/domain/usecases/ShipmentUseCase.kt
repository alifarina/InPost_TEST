package pl.inpost.recruitmenttask.domain.usecases

import pl.inpost.recruitmenttask.network.model.ShipmentNetwork

interface ShipmentUseCase {

    fun sortShipments(listOfShipments: List<ShipmentNetwork>): List<ShipmentNetwork>

    fun groupShipments(shipmentList: List<ShipmentNetwork>): HashMap<String, List<ShipmentNetwork>>
}