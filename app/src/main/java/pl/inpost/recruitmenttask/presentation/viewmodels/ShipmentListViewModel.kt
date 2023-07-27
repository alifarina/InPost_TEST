package pl.inpost.recruitmenttask.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.inpost.recruitmenttask.data.repository.ShipmentRepositoryImpl
import pl.inpost.recruitmenttask.domain.repository.ShipmentRepository
import pl.inpost.recruitmenttask.domain.usecases.ShipmentUseCase
import pl.inpost.recruitmenttask.network.model.ShipmentNetwork
import pl.inpost.recruitmenttask.network.model.ShipmentStatus
import pl.inpost.recruitmenttask.usecases.ShipmentListUseCase
import pl.inpost.recruitmenttask.util.toShipmentNetworkModel
import javax.inject.Inject

@HiltViewModel
class ShipmentListViewModel @Inject constructor(
    private val shipmentRepository: ShipmentRepositoryImpl, private val useCase: ShipmentListUseCase
) : ViewModel() {

    private val mutableViewState = MutableLiveData<HashMap<String, List<ShipmentNetwork>>>()
    val viewState: LiveData<HashMap<String, List<ShipmentNetwork>>> = mutableViewState

    init {
        fetchData()
        observeChangesToData()
    }

    private fun observeChangesToData() {
        shipmentRepository.observeShipmentsData().observeForever { listOfShipments ->
            listOfShipments?.let { listSh ->
                val shipmentList = listSh.map { shipment -> shipment.toShipmentNetworkModel() }
                // perform sort
                val sortedList = useCase.sortShipments(shipmentList)
                // perform grouping
                val mappedList = useCase.groupShipments(sortedList)
                mutableViewState.postValue(mappedList)
            }
        }
    }

    fun fetchData() {
        viewModelScope.launch(Dispatchers.Default) {
            val shipmentList = shipmentRepository.getAllShipments()
            // perform sort
            val sortedList = useCase.sortShipments(shipmentList)
            // perform grouping
            val mappedList = useCase.groupShipments(sortedList)
            mutableViewState.postValue(mappedList)

        }
    }

    fun refreshData() {
        viewModelScope.launch(Dispatchers.Default) {
            val shipmentList = shipmentRepository.refreshShipments()
            // perform sort
            val sortedList = useCase.sortShipments(shipmentList)
            // perform grouping
            val mappedList = useCase.groupShipments(sortedList)
            mutableViewState.postValue(mappedList)

        }
    }

    fun setItemArchived(number: String) {
        viewModelScope.launch(Dispatchers.Default) {
            shipmentRepository.archiveItem(shipment_number = number)
        }
    }
}
