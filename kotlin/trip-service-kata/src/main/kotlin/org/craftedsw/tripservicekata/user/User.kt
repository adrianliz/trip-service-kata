package org.craftedsw.tripservicekata.user

import org.craftedsw.tripservicekata.trip.Trip
import java.util.*

class User {
    private val _trips: MutableList<Trip> = ArrayList()
    val trips: List<Trip>
        get() = _trips

    private val _friends: MutableList<User> = ArrayList()
    val friends: List<User>
        get() = _friends

    fun addFriend(user: User) = _friends.add(user)

    fun addTrip(trip: Trip) = _trips.add(trip)

    fun isFriendOf(user: User): Boolean {
        var isFriend = false
        for (friend in user.friends) {
            if (friend == this) {
                isFriend = true
                break
            }
        }
        return isFriend
    }
}
