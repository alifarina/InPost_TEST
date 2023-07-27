package pl.inpost.recruitmenttask.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import pl.inpost.recruitmenttask.network.model.CustomerNetwork
import pl.inpost.recruitmenttask.network.model.EventLogNetwork
import pl.inpost.recruitmenttask.network.model.OperationsNetwork
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*


fun String.toEventLogNetwork(): List<EventLogNetwork> {
    return Gson().fromJson(this, object : TypeToken<ArrayList<EventLogNetwork>>() {}.type);
}

fun String?.toCustomerNetwork(): CustomerNetwork? {
    this?.let {
        return Gson().fromJson(it, CustomerNetwork::class.java)
    }?: kotlin.run {
        return null
    }

}

fun String.toOperationsNetwork(): OperationsNetwork = Gson().fromJson(this, OperationsNetwork::class.java)

fun String?.toZoneDateTime() : ZonedDateTime? {
    return if (this != null && !this.equals("null",ignoreCase = true)){
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val date: LocalDate = LocalDate.parse(this, formatter)

        //  val zdtWithZoneOffset = ZonedDateTime.parse(this, formatter)

        date.atStartOfDay(ZoneId.systemDefault());
    }else{
        null
    }


}