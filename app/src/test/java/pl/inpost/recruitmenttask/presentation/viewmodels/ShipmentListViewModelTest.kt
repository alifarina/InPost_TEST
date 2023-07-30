package pl.inpost.recruitmenttask.presentation.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import pl.inpost.recruitmenttask.data.local.entities.Shipment
import pl.inpost.recruitmenttask.data.repository.ShipmentRepositoryImpl
import pl.inpost.recruitmenttask.domain.repository.ShipmentRepository
import pl.inpost.recruitmenttask.network.model.ShipmentNetwork
import pl.inpost.recruitmenttask.usecases.ShipmentListUseCase

@RunWith(JUnit4::class)
class ShipmentListViewModelTest {

    private lateinit var viewModel: ShipmentListViewModel
    @Mock
    private lateinit var shipmentRepo : ShipmentRepositoryImpl
    @Mock
    private lateinit var useCase : ShipmentListUseCase

    private  var liveDataListShipment = MutableLiveData<List<Shipment>>()


    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)
        Mockito.`when`(shipmentRepo.observeShipmentsData()).thenReturn(liveDataListShipment)
        viewModel = ShipmentListViewModel(shipmentRepo,useCase)

    }

    @Test
    fun checkObserver(){
        val testMap = HashMap<String,List<ShipmentNetwork>>()
        testMap["one"] = emptyList()
        testMap["two"] = emptyList()
        viewModel.setLiveData(testMap)

        assertEquals(testMap, viewModel.viewState.value)
    }

    @Test
    fun test_getShipments() = runTest {
        Mockito.`when`(shipmentRepo.getAllShipments()).thenReturn(emptyList())
        viewModel.fetchData()
        // to run all scheduled coroutines before any assertion
        testDispatcher.scheduler.advanceUntilIdle()

        val listOfShipments = viewModel.viewState.value
        if (listOfShipments != null) {
            assertEquals(0,listOfShipments.size)
        }
    }

    @Test
    fun test_refreshShipments() = runTest {
        Mockito.`when`(shipmentRepo.refreshShipments()).thenReturn(emptyList())
        viewModel.refreshData()
        // to run all scheduled coroutines before any assertion
        testDispatcher.scheduler.advanceUntilIdle()

        val listOfShipments = viewModel.viewState.value
        if (listOfShipments != null) {
            assertEquals(0,listOfShipments.size)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}