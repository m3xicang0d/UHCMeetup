package dev.ukry.api.handler

/**
 * @author UKry
 * Created: 06/10/2022
 * Project UHCMeetup
 **/

abstract class Handler {

    open fun preInit() {}

    abstract fun init()

    open fun stop() {}
}