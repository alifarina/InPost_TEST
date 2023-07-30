package pl.inpost.recruitmenttask.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.inpost.recruitmenttask.data.local.entities.Shipment
import pl.inpost.recruitmenttask.data.repository.ShipmentRepositoryImpl
import pl.inpost.recruitmenttask.network.model.ShipmentNetwork
import pl.inpost.recruitmenttask.usecases.ShipmentListUseCase
import pl.inpost.recruitmenttask.util.toShipmentNetworkModel
import javax.inject.Inject

@HiltViewModel
class ShipmentListViewModel @Inject constructor(
    private val shipmentRepository: ShipmentRepositoryImpl, private val useCase: ShipmentListUseCase
) : ViewModel() {


    private val mutableViewState = MutableLiveData<HashMap<String, List<ShipmentNetwork>>>()
    val viewState: LiveData<HashMap<String, List<ShipmentNetwork>>> = mutableViewState

    private val observer = Observer<List<Shipment>> { listOfShipments ->
        listOfShipments?.let { listSh ->
            val shipmentList = listSh.map { shipment -> shipment.toShipmentNetworkModel() }
            sortAndGroup(shipmentList)
        }
    }

    private fun sortAndGroup(shipmentList: List<ShipmentNetwork>) {
        // perform sort
        val sortedList = useCase.sortShipments(shipmentList)
        // perform grouping
        val mappedList = useCase.groupShipments(sortedList)
        setLiveData(mappedList)
    }

    init {
        fetchData()
        observeChangesToData()
    }

    private fun observeChangesToData() {
        shipmentRepository.observeShipmentsData().observeForever(observer)
    }

    fun setLiveData(data: HashMap<String, List<ShipmentNetwork>>) {
        mutableViewState.postValue(data)
    }

    fun fetchData() {
        viewModelScope.launch(Dispatchers.Default) {
            val shipmentList = shipmentRepository.getAllShipments()
            // perform sort
            sortAndGroup(shipmentList)

        }
    }

    fun refreshData() {
        viewModelScope.launch(Dispatchers.Default) {
            val shipmentList = shipmentRepository.refreshShipments()
            sortAndGroup(shipmentList)
        }
    }

    fun setItemArchived(number: String) {
        viewModelScope.launch(Dispatchers.Default) {
            shipmentRepository.archiveItem(shipment_number = number)
        }
    }
}
