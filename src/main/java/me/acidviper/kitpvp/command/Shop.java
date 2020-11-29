package me.acidviper.kitpvp.command;

import me.acidviper.kitpvp.KitPVP;
import me.acidviper.kitpvp.menu.ShopMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Shop implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (!sender.hasPermission("kitpvp.shop")) {
                sender.sendMessage(ChatColor.RED + "No Permission.");
                return false;
            }

            sender.sendMessage(ChatColor.GREEN + "Opening shop menu...");
            Bukkit.getPluginManager().registerEvents(new ShopMenu((Player) sender), KitPVP.getInstance());

        } else sender.sendMessage(ChatColor.RED + "You must be a player to use this command!");

        return false;
      }
    }
