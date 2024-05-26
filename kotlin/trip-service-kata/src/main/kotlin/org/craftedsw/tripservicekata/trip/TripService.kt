package org.craftedsw.tripservicekata.trip

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException
import org.craftedsw.tripservicekata.user.User
import org.craftedsw.tripservicekata.user.UserSessionProvider

class TripService(
    private val tripsRepository: TripsRepository,
    private val userSessionProvider: UserSessionProvider,
) {
    fun getTripsByUser(user: User): List<Trip> {
        val loggedUser: User = getLoggedUser() ?: throw UserNotLoggedInException()
        if (!loggedUser.isFriendOf(user)) return noTrips()
        return findTrips(user)
    }

    private fun noTrips(): List<Trip> = listOf()

    private fun findTrips(user: User) = tripsRepository.findBy(user)

    private fun getLoggedUser() = userSessionProvider.findLoggedUser()
}
