package pl.inpost.recruitmenttask.data.repository

import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations
import pl.inpost.recruitmenttask.data.local.DatabaseHolder
import pl.inpost.recruitmenttask.domain.repository.ShipmentRepository
import pl.inpost.recruitmenttask.network.api.ShipmentApi

@RunWith(JUnit4::class)
class ShipmentRepositoryImplTest {

    @Mock
    private lateinit var databaseHolder: DatabaseHolder

    @Mock
    private lateinit var shipmentApi: ShipmentApi

    private lateinit var shipmentRepo: ShipmentRepository

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        shipmentRepo = ShipmentRepositoryImpl(databaseHolder, shipmentApi)
    }

    @Test
    fun getShipmentsFromApi() = runTest {
        Mockito.`when`(databaseHolder.getShipmentsDao().getAllShipments()).thenReturn(emptyList())
        Mockito.`when`(shipmentApi.getShipments()).thenReturn(emptyList())

        val listShipments = shipmentRepo.getAllShipments()

        assertEquals(0,listShipments.size)
        //Mockito.verify(databaseHolder, times(1)).getShipmentsDao().getAllShipments()

    }

    @After
    fun tearDown() {

    }
}