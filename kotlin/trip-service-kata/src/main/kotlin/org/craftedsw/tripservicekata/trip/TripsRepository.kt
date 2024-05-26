package org.craftedsw.tripservicekata.trip

import org.craftedsw.tripservicekata.user.User

interface TripsRepository {
    fun findBy(user: User): List<Trip>
}
