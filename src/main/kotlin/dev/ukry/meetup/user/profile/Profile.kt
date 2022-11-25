package dev.ukry.meetup.user.profile

import com.google.gson.Gson
import com.google.gson.JsonObject
import dev.ukry.api.json.JsonChain
import dev.ukry.meetup.Burrito
import dev.ukry.meetup.user.state.PlayerState
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
            save()
        }
    var kills = 0
        set(value) {
            field = value
            save()
        }
    var deaths = 0
        set(value) {
            field = value
            save()
        }
    var wins = 0
        set(value) {
            field = value
            save()
        }


    fun addKill() {
        kills++
    }

    fun addDeath() {
        deaths++
    }

    fun addWin() {
        wins++
    }

    fun save() {
        Burrito.getInstance().profileHandler.profiles.replace(uuid, this)
    }

    fun serialize() : JsonObject {
        val chain = JsonChain()

        val fields = javaClass.declaredFields
        for(field in fields) {
            chain.addProperty(field.name, field.get(this))
        }
        return chain.get()
    }

    companion object {
        @JvmStatic
        fun valueOf(obj : JsonObject) : Profile {
            val toReturn = Profile(UUID.randomUUID())
            val gson = Gson()
            obj.entrySet().forEach {
                val field = Profile::class.java.getDeclaredField(it.key)
                val acc = field.isAccessible
                field.isAccessible = true
                field.set(toReturn, gson.fromJson(it.value, field.type))
                field.isAccessible = acc
            }
            return toReturn
        }
    }
}