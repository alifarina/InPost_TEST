package pl.inpost.recruitmenttask.util

import pl.inpost.recruitmenttask.data.local.entities.Shipment
import pl.inpost.recruitmenttask.network.model.CustomerNetwork
import pl.inpost.recruitmenttask.network.model.ShipmentNetwork


fun Shipment.toShipmentNetworkModel() = ShipmentNetwork(
        number = number,
        shipmentType = shipmentType,
        status = status,
        eventLog = eventLog.toEventLogNetwork(),
        openCode = openCode,
        expiryDate = expiryDate?.toZoneDateTime(),
        storedDate = storedDate?.toZoneDateTime(),
        pickUpDate = pickUpDate?.toZoneDateTime(),
        receiver = receiver?.toCustomerNetwork(),
        sender = sender.toCustomerNetwork(),
        operations = operations.toOperationsNetwork())







