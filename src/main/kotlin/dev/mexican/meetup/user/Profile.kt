package dev.mexican.meetup.user

import com.google.gson.JsonObject
import dev.ukry.api.json.JsonChain
import java.util.*

/**
 * @author UKry
 * Created: 26/09/2022
 * Project UHCMeetup
 **/

class Profile(private val uuid : UUID) {

    var wins = 0
    var kills = 0
    var deaths = 0

    fun serialize() : JsonObject {
        return JsonChain()
            .addProperty("UUID", uuid.toString())
            .addProperty("WINS", wins)
            .addProperty("KILLS", kills)
            .addProperty("DEATHS", deaths)
            .get()
    }
}