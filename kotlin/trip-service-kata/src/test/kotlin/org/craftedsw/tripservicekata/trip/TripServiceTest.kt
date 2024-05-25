package org.craftedsw.tripservicekata.trip

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException
import org.craftedsw.tripservicekata.user.User
import org.junit.Assert.assertThrows
import kotlin.test.Test

class TripServiceTest {
    @Test
    fun `should throw UserNotLoggedInException when user is not logged in`() {
        val notLoggedUser = User()

        assertThrows(UserNotLoggedInException::class.java) {
            TripService().getTripsByUser(notLoggedUser)
        }
    }
}
