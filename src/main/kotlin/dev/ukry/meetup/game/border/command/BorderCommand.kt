package dev.ukry.meetup.game.border.command

import dev.ukry.meetup.Burrito
import me.gleeming.command.Command
import me.gleeming.command.paramter.Param
import org.bukkit.command.CommandSender

/**
 * @author UKry
 * Created: 04/10/2022
 * Project UHCMeetup
 **/

class BorderCommand {

    @Command(names = ["border"], permission = "command.border")
    fun execute(sender : CommandSender) {
        sender.sendMessage("")
    }

    @Command(names = ["border set"], permission = "command.border")
    fun execute(sender : CommandSender, @Param(name = "size") size : Int) {
        val actualBorder = Burrito.getInstance().gameHandler.actualGame!!.border.actualBorder
        if(actualBorder == size) {
            sender.sendMessage("Borde en ese tamaño")
            return
        }
        if(size > actualBorder) {
            sender.sendMessage("El borde no puede ser mayor")
            return
        }
        Burrito.getInstance().gameHandler.actualGame!!.border.resize()
    }

}