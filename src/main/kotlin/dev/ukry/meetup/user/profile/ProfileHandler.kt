package dev.ukry.meetup.user.profile

import org.bukkit.entity.Player
import java.util.*

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

    fun loadAll() {
    }

    fun saveAll() {

    }
}