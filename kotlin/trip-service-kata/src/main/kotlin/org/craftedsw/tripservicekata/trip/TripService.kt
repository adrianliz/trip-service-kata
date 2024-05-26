package org.craftedsw.tripservicekata.trip

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException
import org.craftedsw.tripservicekata.user.User
import org.craftedsw.tripservicekata.user.UserSession

open class TripService {
    fun getTripsByUser(user: User): List<Trip> {
        var tripList: List<Trip> = listOf()
        val loggedUser: User = getLoggedUser() ?: throw UserNotLoggedInException()
        if (loggedUser.isFriendOf(user)) {
            tripList = findTrips(user)
        }
        return tripList
    }

    open fun findTrips(user: User) = TripDAO.findTripsByUser(user)

    open fun getLoggedUser() = UserSession.instance.loggedUser
}
