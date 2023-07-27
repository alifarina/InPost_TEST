package pl.inpost.recruitmenttask.data.local.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "shipment")
data class Shipment(
    @PrimaryKey @NonNull val number: String,
    @ColumnInfo(name = "expiry_date") val expiryDate: String?,
    @ColumnInfo(name = "stored_date") val storedDate: String?,
    @ColumnInfo(name = "open_code") val openCode: String,
    @ColumnInfo(name = "type") val shipmentType: String,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(name = "pickup_date") val pickUpDate: String?,
    @ColumnInfo(name = "operations") val operations: String,
    @ColumnInfo(name = "event_log") val eventLog: String,
    @ColumnInfo(name = "sender") val sender: String,
    @ColumnInfo(name = "receiver") val receiver: String?,
    @ColumnInfo(name = "is_archived") val is_archived : Boolean? = false
)
