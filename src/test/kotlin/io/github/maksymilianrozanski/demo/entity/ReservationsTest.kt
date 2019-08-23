package io.github.maksymilianrozanski.demo.entity

import org.junit.Assert
import org.junit.Test
import java.sql.Timestamp

internal class ReservationsTest {

    @Test
    fun blankValuesTest() {
        val reservation = Reservations(title = "Example title", description = "example description",
                start = Timestamp(1566576710785), end = Timestamp(1566576725956))
        Assert.assertEquals(0, reservation.id)
        Assert.assertEquals("Example title", reservation.title)
        Assert.assertEquals("example description", reservation.description)
        Assert.assertEquals("", reservation.user)
        Assert.assertTrue(Timestamp(1566576710785).equals(reservation.start))
        Assert.assertTrue(Timestamp(1566576725956).equals(reservation.end))
    }

    @Test
    fun allValuesTest() {
        val reservation = Reservations(id = 12345, title = "Example title", description = "example description",
                user = "Example user", start = Timestamp(1566576710785), end = Timestamp(1566576725956))
        Assert.assertEquals(12345, reservation.id)
        Assert.assertEquals("Example title", reservation.title)
        Assert.assertEquals("example description", reservation.description)
        Assert.assertEquals("Example user", reservation.user)
        Assert.assertTrue(Timestamp(1566576710785).equals(reservation.start))
        Assert.assertTrue(Timestamp(1566576725956).equals(reservation.end))
    }
}
