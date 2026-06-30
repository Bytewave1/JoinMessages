package dev.bytewave.joinmessages

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class ReloadCommand(private val plugin: JoinMessages) : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (!sender.hasPermission("joinmessages.reload")) {
            sender.sendRichMessage("<red>No permission.")
            return true
        }

        plugin.reload()
        sender.sendRichMessage("<green>Config reloaded.")
        return true
    }
}
