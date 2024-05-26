package org.craftedsw.tripservicekata.trip

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException
import org.craftedsw.tripservicekata.user.User
import org.craftedsw.tripservicekata.user.UserSession

open class TripService {
    fun getTripsByUser(user: User): List<Trip> {
        val loggedUser: User = getLoggedUser() ?: throw UserNotLoggedInException()
        if (!loggedUser.isFriendOf(user)) return noTrips()
        return findTrips(user)
    }

    private fun noTrips(): List<Trip> = listOf()

    open fun findTrips(user: User) = TripDAO.findTripsByUser(user)

    open fun getLoggedUser() = UserSession.instance.loggedUser
}
