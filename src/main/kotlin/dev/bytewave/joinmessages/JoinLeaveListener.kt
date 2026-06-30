package dev.bytewave.joinmessages

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class JoinLeaveListener(private val plugin: JoinMessages) : Listener {

    private val miniMessage = MiniMessage.miniMessage()

    @EventHandler(priority = EventPriority.HIGH)
    fun onJoin(event: PlayerJoinEvent) {
        val config = plugin.config
        val player = event.player

        val isFirstJoin = !player.hasPlayedBefore()

        val raw = if (isFirstJoin && config.getBoolean("first-join.enabled", true)) {
            config.getString("first-join.message") ?: return
        } else {
            config.getString("join-message") ?: return
        }

        val formatted = formatMessage(raw, player.name)
        event.joinMessage(formatted)

        if (config.getBoolean("sounds.join.enabled", false)) {
            val soundName = config.getString("sounds.join.sound", "ENTITY_PLAYER_LEVELUP") ?: return
            val volume = config.getDouble("sounds.join.volume", 1.0).toFloat()
            val pitch = config.getDouble("sounds.join.pitch", 1.0).toFloat()

            try {
                val sound = Sound.valueOf(soundName)
                plugin.server.onlinePlayers.forEach { it.playSound(it.location, sound, volume, pitch) }
            } catch (_: IllegalArgumentException) {
                plugin.logger.warning("Invalid sound: $soundName")
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    fun onQuit(event: PlayerQuitEvent) {
        val config = plugin.config
        val player = event.player

        val raw = config.getString("leave-message") ?: return
        val formatted = formatMessage(raw, player.name)
        event.quitMessage(formatted)

        if (config.getBoolean("sounds.leave.enabled", false)) {
            val soundName = config.getString("sounds.leave.sound", "ENTITY_ENDERMAN_TELEPORT") ?: return
            val volume = config.getDouble("sounds.leave.volume", 1.0).toFloat()
            val pitch = config.getDouble("sounds.leave.pitch", 1.0).toFloat()

            try {
                val sound = Sound.valueOf(soundName)
                plugin.server.onlinePlayers.forEach { it.playSound(it.location, sound, volume, pitch) }
            } catch (_: IllegalArgumentException) {
                plugin.logger.warning("Invalid sound: $soundName")
            }
        }
    }

    private fun formatMessage(raw: String, playerName: String): Component {
        val replaced = raw.replace("%player%", playerName)
        val converted = convertHexColors(replaced)
        return miniMessage.deserialize(converted)
    }

    private fun convertHexColors(input: String): String {
        val hexPattern = Regex("&#([A-Fa-f0-9]{6})")
        var result = input

        result = hexPattern.replace(result) { match ->
            "<color:#${match.groupValues[1]}>"
        }

        val legacyCodes = mapOf(
            "&0" to "<black>", "&1" to "<dark_blue>", "&2" to "<dark_green>",
            "&3" to "<dark_aqua>", "&4" to "<dark_red>", "&5" to "<dark_purple>",
            "&6" to "<gold>", "&7" to "<gray>", "&8" to "<dark_gray>",
            "&9" to "<blue>", "&a" to "<green>", "&b" to "<aqua>",
            "&c" to "<red>", "&d" to "<light_purple>", "&e" to "<yellow>",
            "&f" to "<white>", "&l" to "<bold>", "&m" to "<strikethrough>",
            "&n" to "<underlined>", "&o" to "<italic>", "&r" to "<reset>"
        )

        legacyCodes.forEach { (code, tag) ->
            result = result.replace(code, tag, ignoreCase = true)
        }

        return result
    }
}
