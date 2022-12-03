/*
 * Copyright (c) 2020. Joel Evans
 *
 * Use and or redistribution of compiled JAR file and or source code is permitted only if given
 * explicit permission from original author: Joel Evans
 */

package dev.ukry.menu.utils

import dev.ukry.meetup.Burrito
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask

object Tasks {

    @JvmStatic
    fun sync(lambda: () -> Unit): BukkitTask {
        return Burrito.getInstance().server.scheduler.runTask(Burrito.getInstance()) {
            lambda.invoke()
        }
    }

    @JvmStatic
    fun delayed(delay: Long, lambda: () -> Unit): BukkitTask {
        return Burrito.getInstance().server.scheduler.runTaskLater(Burrito.getInstance(), {
            lambda.invoke()
        }, delay)
    }

    @JvmStatic
    fun delayed(delay: Long, runnable: Runnable): BukkitTask {
        return Burrito.getInstance().server.scheduler.runTaskLater(Burrito.getInstance(), runnable, delay)
    }

    @JvmStatic
    fun delayed(delay: Long, runnable: BukkitRunnable): BukkitTask {
        return runnable.runTaskLater(Burrito.getInstance(), delay)
    }

    @JvmStatic
    fun timer(delay: Long, interval: Long, lambda: () -> Unit): BukkitTask {
        return Burrito.getInstance().server.scheduler.runTaskTimer(Burrito.getInstance(), {
            lambda.invoke()
        }, delay, interval)
    }

    @JvmStatic
    fun timer(delay: Long, interval: Long, runnable: Runnable): BukkitTask {
        return Burrito.getInstance().server.scheduler.runTaskTimer(Burrito.getInstance(), runnable, delay, interval)
    }

    @JvmStatic
    fun timer(delay: Long, interval: Long, runnable: BukkitRunnable): BukkitTask {
        return runnable.runTaskTimer(Burrito.getInstance(), delay, interval)
    }

    @JvmStatic
    fun async(lambda: () -> Unit): BukkitTask {
        return Burrito.getInstance().server.scheduler.runTaskAsynchronously(Burrito.getInstance()) {
            lambda.invoke()
        }
    }

    @JvmStatic
    fun asyncDelayed(delay: Long, lambda: () -> Unit): BukkitTask {
        return Burrito.getInstance().server.scheduler.runTaskLaterAsynchronously(Burrito.getInstance(), {
            lambda.invoke()
        }, delay)
    }

    @JvmStatic
    fun asyncDelayed(runnable: Runnable, delay: Long): BukkitTask {
        return Burrito.getInstance().server.scheduler.runTaskLaterAsynchronously(Burrito.getInstance(), runnable, delay)
    }

    @JvmStatic
    fun asyncDelayed(runnable: BukkitRunnable, delay: Long): BukkitTask {
        return runnable.runTaskLaterAsynchronously(Burrito.getInstance(), delay)
    }

    @JvmStatic
    fun asyncTimer(delay: Long, interval: Long, lambda: () -> Unit): BukkitTask {
        return Burrito.getInstance().server.scheduler.runTaskTimerAsynchronously(Burrito.getInstance(), {
            lambda.invoke()
        }, delay, interval)
    }

    @JvmStatic
    fun asyncTimer(runnable: Runnable, delay: Long, interval: Long): BukkitTask {
        return Burrito.getInstance().server.scheduler.runTaskTimerAsynchronously(Burrito.getInstance(), runnable, delay, interval)
    }

    @JvmStatic
    fun asyncTimer(runnable: BukkitRunnable, delay: Long, interval: Long): BukkitTask {
        return runnable.runTaskTimerAsynchronously(Burrito.getInstance(), delay, interval)
    }

}