package me.acidviper.kitpvp.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemCreator {
    public static ItemStack createItem(Material type, String name, List<String> description) {
        ItemStack toReturn = new ItemStack(type);
        ItemMeta meta = toReturn.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(description);

        toReturn.setItemMeta(meta);

        return toReturn;
    }
}
