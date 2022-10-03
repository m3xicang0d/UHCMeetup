package dev.kry.meetup.storage

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
}