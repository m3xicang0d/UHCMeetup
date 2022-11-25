package dev.ukry.meetup.scoreboard

import org.bukkit.entity.Player
import java.util.function.BiConsumer

/**
 * @author UKry
 * Created: 06/10/2022
 * Project UHCMeetup
 **/

abstract class Scoreboard : BiConsumer<Player, MutableList<String>>