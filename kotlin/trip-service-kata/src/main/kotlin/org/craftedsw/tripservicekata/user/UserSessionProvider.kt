package org.craftedsw.tripservicekata.user

interface UserSessionProvider {
    fun findLoggedUser(): User?
}
