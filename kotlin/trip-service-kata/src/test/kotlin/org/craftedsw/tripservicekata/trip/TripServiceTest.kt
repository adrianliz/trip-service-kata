package org.craftedsw.tripservicekata.trip

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException
import org.craftedsw.tripservicekata.user.User
import org.junit.Assert.assertThrows
import kotlin.test.Test

class TripServiceTest {
    class TripServiceWithLoggedUser(val user: User?) : TripService() {
        companion object {
            fun noLoggedUser() = TripServiceWithLoggedUser(null)
        }

        override fun getLoggedUser() = user
    }

    @Test
    fun `should throw UserNotLoggedInException when user is not logged in`() {
        val notLoggedUser = User()
        val tripService = TripServiceWithLoggedUser.noLoggedUser()

        assertThrows(UserNotLoggedInException::class.java) {
            tripService.getTripsByUser(notLoggedUser)
        }
    }
}
