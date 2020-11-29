package me.acidviper.kitpvp.command;

import me.acidviper.kitpvp.KitPVP;
import me.acidviper.kitpvp.kit.KitBase;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SetIcon implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (!sender.hasPermission("kitpvp.seticons")) {
                sender.sendMessage(ChatColor.RED + "No Permission.");
                return false;
            }

            if (args.length != 1) {
                sender.sendMessage(ChatColor.RED + "Usage: /seticon <Kit Name>");
                return false;
            }

            if (((Player) sender).getInventory().getItemInMainHand() == null) {
                sender.sendMessage(ChatColor.RED + "You must hold the item you want to set as the icon!");
                return false;
            }

            for (KitBase kit : KitBase.getKits()) {
                if (kit.getName().equalsIgnoreCase(args[0])) {
                    ItemStack stack = new ItemStack(((Player) sender).getInventory().getItemInMainHand().getType());
                    ItemMeta meta = stack.getItemMeta();

                    meta.setLore(((Player) sender).getInventory().getItemInMainHand().getItemMeta().getLore());
                    meta.setDisplayName(ChatColor.WHITE + kit.getName());

                    stack.setItemMeta(meta);

                    kit.setIcon(stack);
                    KitPVP.getInstance().getDatabase().set(kit.getName() + ".ICON", stack);

                    sender.sendMessage(ChatColor.GREEN + "Icon successfully set");
                    return false;
                }
            }

            sender.sendMessage(ChatColor.RED + "Could not find that kit, you may have misspelled the name!");
            return false;

        } else sender.sendMessage(ChatColor.RED + "You must be a player to use this command!");

        return false;
    }
}
