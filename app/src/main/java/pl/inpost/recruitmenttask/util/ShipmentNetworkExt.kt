package pl.inpost.recruitmenttask.util

import com.google.gson.Gson
import pl.inpost.recruitmenttask.data.local.entities.Shipment
import pl.inpost.recruitmenttask.data.local.entities.ShipmentUpdate
import pl.inpost.recruitmenttask.network.model.ShipmentNetwork

fun ShipmentNetwork.toEntity() = Shipment(
    number = number,
    expiryDate = expiryDate.toString(),
    storedDate = storedDate.toString(),
    openCode = openCode.toString(),
    shipmentType = shipmentType,
    status = status,
    pickUpDate = pickUpDate.toString(),
    operations = Gson().toJson(operations),
    eventLog = Gson().toJson(eventLog),
    sender = Gson().toJson(sender),
    receiver = Gson().toJson(receiver)
)

fun ShipmentNetwork.toEntityUpdate() = ShipmentUpdate(
    number = number,
    expiryDate = expiryDate.toString(),
    storedDate = storedDate.toString(),
    openCode = openCode.toString(),
    shipmentType = shipmentType,
    status = status,
    pickUpDate = pickUpDate.toString(),
    operations = Gson().toJson(operations),
    eventLog = Gson().toJson(eventLog),
    sender = Gson().toJson(sender),
    receiver = Gson().toJson(receiver)
)