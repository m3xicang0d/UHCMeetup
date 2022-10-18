package dev.mexican.meetup.user

import dev.mexican.meetup.Burrito
import dev.mexican.meetup.user.state.PlayerState
import java.util.*

/**
 * @author UKry
 * Created: 26/09/2022
 * Project UHCMeetup
 **/

class Profile(private val uuid: UUID) {

    var state = PlayerState.PLAYING
        set(value) {
            field = value
            Burrito.getInstance().profileHandler.profiles.replace(uuid, this)
        }
    var kills = 0
        set(value) {
            field = value
            Burrito.getInstance().profileHandler.profiles.replace(uuid, this)
        }
}