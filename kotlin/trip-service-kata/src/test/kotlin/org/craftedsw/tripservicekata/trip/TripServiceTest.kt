package org.craftedsw.tripservicekata.trip

import io.mockk.every
import io.mockk.mockk
import org.craftedsw.tripservicekata.exception.UserNotLoggedInException
import org.craftedsw.tripservicekata.user.User
import org.junit.Assert.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TripServiceTest {
    class TripServiceWith(val user: User?, tripsRepository: TripsRepository = mockk()) : TripService(tripsRepository) {
        companion object {
            fun noLoggedUser() = TripServiceWith(null, mockk())
        }

        override fun getLoggedUser() = user
    }

    private val tripsRepository = mockk<TripsRepository>()

    private fun friendUser(loggedUser: User): User {
        val friendUser = User()
        friendUser.addFriend(loggedUser)
        return friendUser
    }

    @Test
    fun `should throw UserNotLoggedInException when user is not logged in`() {
        val notLoggedUser = User()
        val tripService = TripServiceWith.noLoggedUser()

        assertThrows(UserNotLoggedInException::class.java) {
            tripService.getTripsByUser(notLoggedUser)
        }
    }

    @Test
    fun `should return no trips when user is not friend with logged user`() {
        val loggedUser = User()
        val notFriendUser = User()
        val tripService = TripServiceWith(loggedUser)

        val trips = tripService.getTripsByUser(notFriendUser)

        assertTrue { trips.isEmpty() }
    }

    @Test
    fun `should return trips of logged user when user is friend with logged user`() {
        val loggedUser = User()
        val friendUser = friendUser(loggedUser)
        val loggedUserTrips = listOf(Trip(), Trip())
        every { tripsRepository.findBy(friendUser) } returns loggedUserTrips
        val tripService = TripServiceWith(loggedUser, tripsRepository)

        val trips = tripService.getTripsByUser(friendUser)

        assertEquals(loggedUserTrips, trips)
    }
}
