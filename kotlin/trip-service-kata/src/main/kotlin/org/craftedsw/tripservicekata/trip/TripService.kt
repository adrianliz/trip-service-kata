package org.craftedsw.tripservicekata.trip

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException
import org.craftedsw.tripservicekata.user.User
import org.craftedsw.tripservicekata.user.UserSession

open class TripService {
    fun getTripsByUser(user: User): List<Trip> {
        var tripList: List<Trip> = ArrayList<Trip>()
        val loggedUser: User? = getLoggedUser()
        var isFriend = false
        if (loggedUser != null) {
            isFriend = isFriend(user, loggedUser)
            if (isFriend) {
                tripList = findTrips(user)
            }
            return tripList
        } else {
            throw UserNotLoggedInException()
        }
    }

    private fun isFriend(
        user: User,
        loggedUser: User?,
    ): Boolean {
        var isFriend = false
        for (friend in user.friends) {
            if (friend == loggedUser) {
                isFriend = true
                break
            }
        }
        return isFriend
    }

    open fun findTrips(user: User) = TripDAO.findTripsByUser(user)

    open fun getLoggedUser() = UserSession.instance.loggedUser
}
