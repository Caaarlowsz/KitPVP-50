package me.acidviper.kitpvp.command;

import me.acidviper.kitpvp.KitPVP;
import me.acidviper.kitpvp.kit.KitBase;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

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
                sender.sendMessage(ChatColor.RED + "Usage: /createkit <Name> <Price> <Default (true/false)>");
                return false;
            }

            List<String> kitList = KitPVP.getInstance().getDatabase().getStringList("KITS");

            if (KitPVP.getInstance().getDatabase().getString("KITS") == null) kitList = new ArrayList<>();

            kitList.add(args[0]);
            KitPVP.getInstance().getDatabase().set("KITS", kitList);
            KitPVP.getInstance().getDatabase().set(args[0] + ".PRICE", Integer.parseInt(args[1]));
            KitPVP.getInstance().getDatabase().set(args[0] + ".DEFAULT", Boolean.valueOf(args[2]));

            ItemStack[] contents = ((Player) sender).getInventory().getContents();
            KitPVP.getInstance().getDatabase().set(args[0] + ".KIT", contents);

            KitPVP.getInstance().getDatabase().set(args[0] + ".HELMET", ((Player) sender).getInventory().getHelmet());
            KitPVP.getInstance().getDatabase().set(args[0] + ".CHESTPLATE", ((Player) sender).getInventory().getChestplate());
            KitPVP.getInstance().getDatabase().set(args[0] + ".LEGGINGS", ((Player) sender).getInventory().getLeggings());
            KitPVP.getInstance().getDatabase().set(args[0] + ".BOOTS", ((Player) sender).getInventory().getBoots());

            sender.sendMessage(ChatColor.GREEN + "You've created a kit with the name " + args[0] + ", with the price of " + args[1] + " coins.");

            KitBase.getKits().clear();
            KitPVP.getInstance().setUpKits();
        } else {
            sender.sendMessage(ChatColor.RED + "You must be a player to use that command!");
        }
        return false;
    }
}
