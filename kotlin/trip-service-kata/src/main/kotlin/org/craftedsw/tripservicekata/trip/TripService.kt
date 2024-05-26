package org.craftedsw.tripservicekata.trip

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException
import org.craftedsw.tripservicekata.user.User
import org.craftedsw.tripservicekata.user.UserSession

open class TripService {
    fun getTripsByUser(user: User): List<Trip> {
        var tripList: List<Trip> = ArrayList<Trip>()
        val loggedUser: User? = getLoggedUser()
        var isFriend = false
        if (loggedUser == null) {
            throw UserNotLoggedInException()
        }
        isFriend = loggedUser.isFriendOf(user)
        if (isFriend) {
            tripList = findTrips(user)
        }
        return tripList
    }

    open fun findTrips(user: User) = TripDAO.findTripsByUser(user)

    open fun getLoggedUser() = UserSession.instance.loggedUser
}
