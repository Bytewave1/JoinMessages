package dev.bytewave.joinmessages

import org.bukkit.plugin.java.JavaPlugin

class JoinMessages : JavaPlugin() {

    override fun onEnable() {
        saveDefaultConfig()
        server.pluginManager.registerEvents(JoinLeaveListener(this), this)
        getCommand("joinmessages")?.setExecutor(ReloadCommand(this))
        logger.info("JoinMessages loaded.")
    }

    override fun onDisable() {
        logger.info("JoinMessages disabled.")
    }

    fun reload() {
        reloadConfig()
    }
}
