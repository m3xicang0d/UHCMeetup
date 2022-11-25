package dev.ukry.meetup.storage

import com.google.gson.JsonObject

/**
 * @author UKry
 * Created: 26/09/2022
 * Project UHCMeetup
 **/

interface Storage {

    fun init()

    fun stop()

    fun save()

    fun reload()

    fun save(key : String, value : JsonObject)
}