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
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.doNothing
import pl.inpost.recruitmenttask.data.local.DatabaseHolder
import pl.inpost.recruitmenttask.data.local.dao.ShipmentsDao
import pl.inpost.recruitmenttask.data.local.entities.Shipment
import pl.inpost.recruitmenttask.data.local.entities.ShipmentUpdate
import pl.inpost.recruitmenttask.domain.repository.ShipmentRepository
import pl.inpost.recruitmenttask.network.api.ShipmentApi

@RunWith(JUnit4::class)
class ShipmentRepositoryImplTest {

    @Mock
    private lateinit var databaseHolder: DatabaseHolder

    @Mock
    private lateinit var shipmentApi: ShipmentApi

    private lateinit var shipmentRepo: ShipmentRepository


    private val listOfShipment = mutableListOf<Shipment>()
    private val listOfShipmentUpdate = mutableListOf<ShipmentUpdate>()

    @Mock
    private lateinit var daoMock : ShipmentsDao

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Mockito.`when`(databaseHolder.getShipmentsDao()).thenReturn(daoMock)
        shipmentRepo = ShipmentRepositoryImpl(databaseHolder, shipmentApi)
    }

    @Test
    fun getShipmentsFromDatabase() = runTest {
        Mockito.`when`(databaseHolder.getShipmentsDao().checkShipmentsCount()).thenReturn(3)
        Mockito.`when`(databaseHolder.getShipmentsDao().getAllShipments()).thenReturn(listOfShipment)
        val listShipments = shipmentRepo.getAllShipments()

        assertEquals(0,listShipments.size)

    }

    @Test
    fun getShipmentsFromApi() = runTest {

        Mockito.`when`(shipmentApi.getShipments()).thenReturn(emptyList())
        Mockito.`when`(databaseHolder.getShipmentsDao().checkShipmentsCount()).thenReturn(0)
        Mockito.`when`(databaseHolder.getShipmentsDao().getAllShipments()).thenReturn(listOfShipment)
        doNothing().`when`(daoMock).insertAllShipments(listOfShipment)
        val listShipments = shipmentRepo.getAllShipments()

        assertEquals(0,listShipments.size)

    }

    @Test
    fun refreshDataFromApi() = runTest {

        Mockito.`when`(shipmentApi.getShipments()).thenReturn(emptyList())
        Mockito.`when`(databaseHolder.getShipmentsDao().getAllShipments()).thenReturn(listOfShipment)
        doNothing().`when`(daoMock).updateAllShipments(listOfShipmentUpdate)
        val listShipments = shipmentRepo.refreshShipments()

        assertEquals(0,listShipments.size)

    }

    @After
    fun tearDown() {

    }
}