package dev.mexican.meetup.user

import org.bukkit.entity.Player
import java.util.UUID

/**
 * @author UKry
 * Created: 26/09/2022
 * Project UHCMeetup
 **/

class ProfileHandler {

    val profiles = mutableMapOf<UUID, Profile>()

    fun getOrCreateProfile(player : Player) : Profile {
        return getOrCreateProfile(player.uniqueId)
    }

    fun getOrCreateProfile(uuid : UUID): Profile {
        if(!profiles.containsKey(uuid)) {
            profiles[uuid] = Profile(uuid)
        }
        return profiles[uuid]!!
    }
}