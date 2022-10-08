package dev.mexican.meetup.user

import com.google.gson.JsonObject
import dev.mexican.meetup.user.state.PlayerState
import dev.ukry.api.json.JsonChain
import java.util.*

/**
 * @author UKry
 * Created: 26/09/2022
 * Project UHCMeetup
 **/

class Profile(private val uuid : UUID) {

    var state = PlayerState.LOBBY
    var kills = 0
    var deaths = 0

    fun serialize() : JsonObject {
        return JsonChain()
            .addProperty("UUID", uuid.toString())
            .addProperty("KILLS", kills)
            .addProperty("DEATHS", deaths)
            .get()
    }
}