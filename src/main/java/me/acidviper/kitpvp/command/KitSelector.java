package me.acidviper.kitpvp.command;

import me.acidviper.kitpvp.KitPVP;
import me.acidviper.kitpvp.menu.KitMenu;
import me.acidviper.kitpvp.player.KitPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitSelector implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (!sender.hasPermission("kitpvp.selectkits")) {
                sender.sendMessage(ChatColor.RED + "No Permission.");
                return false;
            }

            if (KitPlayer.getKitPlayers().get(((Player) sender).getUniqueId()).getOwnedKits().size() < 1) {
                sender.sendMessage(ChatColor.RED + "You have no kits!");
                return false;
            }

            sender.sendMessage(ChatColor.GREEN + "Opening kit menu...");
            Bukkit.getPluginManager().registerEvents(new KitMenu((Player) sender), KitPVP.getInstance());

        } else sender.sendMessage(ChatColor.RED + "You must be a player to use this command!");

        return false;
    }
}
