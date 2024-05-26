package org.craftedsw.tripservicekata.trip

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException
import org.craftedsw.tripservicekata.user.User
import org.junit.Assert.assertThrows
import kotlin.test.Test
import kotlin.test.assertTrue

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

    @Test
    fun `should return no trips when user is not friend with logged user`() {
        val loggedUser = User()
        val notFriendUser = User()
        val tripService = TripServiceWithLoggedUser(loggedUser)

        val trips = tripService.getTripsByUser(notFriendUser)

        assertTrue { trips.isEmpty() }
    }
}
