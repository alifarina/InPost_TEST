package pl.inpost.recruitmenttask.domain.usecases

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import pl.inpost.recruitmenttask.network.api.MockShipmentApi
import pl.inpost.recruitmenttask.network.api.ShipmentApi
import pl.inpost.recruitmenttask.usecases.ShipmentListUseCase

class ShipmentListUseCaseTest {

    private lateinit var useCase: ShipmentUseCase


    @Before
    fun setUp() {
        useCase = ShipmentListUseCase()
    }

    @Test
    fun checkSorting() {
        useCase.sortShipments(emptyList())
    }

    @After
    fun tearDown() {
        
    }
}