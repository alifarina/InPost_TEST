package pl.inpost.recruitmenttask.domain.usecases

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import pl.inpost.recruitmenttask.network.api.MockShipmentApi
import pl.inpost.recruitmenttask.network.model.ShipmentNetwork
import pl.inpost.recruitmenttask.network.model.ShipmentStatus
import pl.inpost.recruitmenttask.usecases.ShipmentListUseCase

@RunWith(JUnit4::class)
class ShipmentListUseCaseTest {

    private lateinit var useCase: ShipmentUseCase


    @Before
    fun setUp() {
        useCase = ShipmentListUseCase()
    }

    @Test
    fun checkSortingForStatus() {
        val listShipmentsForStatus = mutableListOf<ShipmentNetwork>()

        listShipmentsForStatus.add(MockShipmentApi.mockShipmentNetwork(status = ShipmentStatus.OUT_FOR_DELIVERY))
        listShipmentsForStatus.add(MockShipmentApi.mockShipmentNetwork(status = ShipmentStatus.READY_TO_PICKUP))
        listShipmentsForStatus.add(MockShipmentApi.mockShipmentNetwork(status = ShipmentStatus.CONFIRMED))

        val resultList = useCase.sortShipments(listShipmentsForStatus)

        assert(resultList.isNotEmpty())
        assert(resultList[0].status == ShipmentStatus.CONFIRMED.name)

    }

    @Test
    fun checkGroupingItems() {
        val listShipmentsForStatus = mutableListOf<ShipmentNetwork>()

        listShipmentsForStatus.add(
            MockShipmentApi.mockShipmentNetwork(
                status = ShipmentStatus.OUT_FOR_DELIVERY,
                operations = MockShipmentApi.mockOperationsNetwork(highlight = true)
            )
        )
        listShipmentsForStatus.add(
            MockShipmentApi.mockShipmentNetwork(
                status = ShipmentStatus.READY_TO_PICKUP,
                operations = MockShipmentApi.mockOperationsNetwork(highlight = true)
            )
        )
        listShipmentsForStatus.add(
            MockShipmentApi.mockShipmentNetwork(
                status = ShipmentStatus.CONFIRMED,
                operations = MockShipmentApi.mockOperationsNetwork(highlight = false)
            )
        )
        listShipmentsForStatus.add(
            MockShipmentApi.mockShipmentNetwork(
                status = ShipmentStatus.DELIVERED,
                operations = MockShipmentApi.mockOperationsNetwork(highlight = false)
            )
        )

        val resultMap = useCase.groupShipments(listShipmentsForStatus)

        // there are two groups , grouped on highlight as true or false
        assert(resultMap.keys.size == 2)
        assert(resultMap.values.size == 2)

    }

    @After
    fun tearDown() {

    }
}