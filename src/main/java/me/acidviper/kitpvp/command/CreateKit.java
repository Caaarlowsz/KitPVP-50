package me.acidviper.kitpvp.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateKit implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (!sender.hasPermission("kitpvp.createkits")) {
                sender.sendMessage(ChatColor.RED + "No permission.");
                return false;
            }

            if (args.length != 3) {
                sender.sendMessage(ChatColor.RED + "Wrong amount of arguments");
                sender.sendMessage(ChatColor.RED + "Usage: /createkit <Name> <Price> <Default>");
                return false;
            }


        } else {
            sender.sendMessage(ChatColor.RED + "You must be a player to use that command!");
        }
        return false;
    }
}
