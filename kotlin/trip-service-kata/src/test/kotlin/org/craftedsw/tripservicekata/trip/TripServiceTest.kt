package org.craftedsw.tripservicekata.trip

import io.mockk.every
import io.mockk.mockk
import org.craftedsw.tripservicekata.exception.UserNotLoggedInException
import org.craftedsw.tripservicekata.user.User
import org.craftedsw.tripservicekata.user.UserSessionProvider
import org.junit.Assert.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TripServiceTest {
    private val tripsRepository = mockk<TripsRepository>()
    private val userSessionProvider = mockk<UserSessionProvider>()
    private val tripService = TripService(tripsRepository, userSessionProvider)

    private fun friendUser(user: User) = User().apply { addFriend(user) }

    private fun givenNoLoggedUser() = every { userSessionProvider.findLoggedUser() } returns null

    private fun givenThereIsLoggedUser(loggedUser: User) = every { userSessionProvider.findLoggedUser() } returns loggedUser

    private fun givenUserHasTrips(
        user: User,
        trips: List<Trip>,
    ) = every { tripsRepository.findBy(user) } returns trips

    private fun anyTrips() = listOf(Trip(), Trip())

    @Test
    fun `should throw UserNotLoggedInException when user is not logged in`() {
        val guest = User()
        givenNoLoggedUser()

        assertThrows(UserNotLoggedInException::class.java) {
            tripService.getTripsByUser(guest)
        }
    }

    @Test
    fun `should return no trips when user is not friend with logged user`() {
        val loggedUser = User()
        val notFriendUser = User()
        givenThereIsLoggedUser(loggedUser)

        val trips = tripService.getTripsByUser(notFriendUser)

        assertTrue { trips.isEmpty() }
    }

    @Test
    fun `should return trips of logged user when user is friend with logged user`() {
        val loggedUser = User()
        val friendUser = friendUser(loggedUser)
        val friendUserTrips = anyTrips()
        givenThereIsLoggedUser(loggedUser)
        givenUserHasTrips(friendUser, friendUserTrips)

        val trips = tripService.getTripsByUser(friendUser)

        assertEquals(friendUserTrips, trips)
    }
}
