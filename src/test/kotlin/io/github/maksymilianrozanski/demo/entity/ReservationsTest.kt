package io.github.maksymilianrozanski.demo.entity

import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import java.sql.Timestamp

internal class ReservationsTest {

    @Test
    fun blankValuesTest() {
        val reservation = Reservations(title = "Example title", description = "example description",
                start = Timestamp(1566576710785), end = Timestamp(1566576725956))
        Assert.assertEquals(0, reservation.reservationId)
        Assert.assertEquals("Example title", reservation.title)
        Assert.assertEquals("example description", reservation.description)
        Assert.assertEquals(null, reservation.user)
        Assert.assertTrue(Timestamp(1566576710785).equals(reservation.start))
        Assert.assertTrue(Timestamp(1566576725956).equals(reservation.end))
    }

    @Test
    fun allValuesTest() {
        val userMock = Mockito.mock(User::class.java)
        val reservation = Reservations(reservationId = 12345, title = "Example title", description = "example description",
                user = userMock, start = Timestamp(1566576710785), end = Timestamp(1566576725956))
        Assert.assertEquals(12345, reservation.reservationId)
        Assert.assertEquals("Example title", reservation.title)
        Assert.assertEquals("example description", reservation.description)
        Assert.assertEquals(userMock, reservation.user)
        Assert.assertTrue(Timestamp(1566576710785).equals(reservation.start))
        Assert.assertTrue(Timestamp(1566576725956).equals(reservation.end))
    }
}
